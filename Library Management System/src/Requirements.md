# Java Practical Exam: Console Library Management System

**Level:** Intermediate
**Suggested time:** 6-10 hours (spread over several sessions is fine)
**Total marks:** 100 (+ 20 bonus)
**Allowed:** Java standard library, your notes, the official Java docs
**Not allowed:** Generics (writing your own generic classes or methods), lambdas/streams, external libraries. You may *use* collections as you already know them.

---

## Scenario

A small community library has been tracking everything on paper. You have been hired to build a console application that lets a librarian manage the library's items and members, and handle borrowing and returning.

The program must be menu-driven, run in a loop until the librarian chooses to exit, and **never crash on invalid input**.

---

## Section A: Model Layer (25 marks)

### A1. `LibraryItem` (abstract class) [8 marks]

Create an abstract class `LibraryItem` with:

- Private fields: `id` (String), `title` (String), `borrowed` (boolean), `dueDate` (LocalDate, null when not borrowed)
- A constructor, getters, and appropriate setters
- Abstract methods:
  - `int getLoanPeriodDays()`
  - `double getDailyLateFee()`
- A concrete  method `double calculateLateFee(LocalDate returnDate)` that uses the two abstract methods above. It returns 0 if the item is not late.
- Properly overridden `equals`, `hashCode` (based on `id` only), and `toString`

### A2. Subclasses [6 marks]

Create three subclasses with the following rules:

| Class | Extra field | Loan period | Late fee per day |
|---|---|---|---|
| `Book` | `author` (String) | 14 days | $0.50 |
| `DVD` | `director` (String) | 7 days | $1.00 |
| `Magazine` | `issueNumber` (int) | 3 days | $0.25 |

Each subclass must override `toString` to include its extra field.

### A3. Interface [3 marks]

Create an interface `Searchable` with one method:

```java
boolean matches(String keyword);
```

`LibraryItem` (or its subclasses) must implement it. A match is a **case-insensitive** partial match on the title. `Book` must also match on author, and `DVD` on director.

### A4. `Member` class [8 marks]

- Private fields: `memberId` (String), `name` (String), `borrowedItems` (an `ArrayList` of `LibraryItem`), `unpaidFees` (double)
- Methods to add/remove a borrowed item, get the current count, add fees, pay fees
- Do **not** return the internal list directly from a getter. Return a copy.
- Properly overridden `equals`, `hashCode` (based on `memberId`), and `toString`

---

## Section B: Custom Exceptions (10 marks)

Create the following checked exceptions, each with a meaningful message passed to `super(...)`:

1. `ItemNotFoundException`
2. `ItemNotAvailableException`
3. `MemberNotFoundException`
4. `MemberLimitExceededException` (used for both the 3-item limit and the unpaid-fees rule, with different messages)

---

## Section C: Library Class (30 marks)

Create a `Library` class that holds all the data and business logic. **It must contain no `System.out` calls and no `Scanner`.** It should be usable with any UI.

### Data storage

- Items: an `ArrayList` of `LibraryItem`
- Members: a `HashMap` keyed by member ID
- Use a `HashSet` (or check `equals`) so that two items with the same ID can never be added

### Required methods [30 marks total]

| Method | Behaviour | Marks |
|---|---|---|
| `addItem(LibraryItem item)` | Rejects duplicate IDs | 3 |
| `removeItem(String id)` | Throws `ItemNotFoundException` if missing. Cannot remove an item that is currently borrowed. | 4 |
| `registerMember(Member m)` | Rejects duplicate member IDs | 3 |
| `searchItems(String keyword)` | Returns all items that match, using `Searchable` | 4 |
| `findItemById(String id)` | Throws `ItemNotFoundException` | 2 |
| `borrowItem(String memberId, String itemId, LocalDate today)` | Enforces all borrowing rules (see below). Sets the due date based on the item's loan period. | 8 |
| `returnItem(String memberId, String itemId, LocalDate today)` | Clears the borrowed state, removes it from the member, calculates the late fee and adds it to the member's unpaid fees. Returns the fee charged. | 6 |.

### Borrowing rules (must all be enforced)

1. A member can hold **at most 3 items** at once.
2. An item that is already borrowed **cannot** be borrowed again.
3. A member with **unpaid fees over $5.00** cannot borrow anything.
4. The member and the item must both exist.

Pass the date in as a parameter instead of calling `LocalDate.now()` inside the method. This makes your code testable.

### Reports

| Method | Returns | Marks |
|---|---|---|
| `getAvailableItems()` | All items not currently borrowed | |
| `getBorrowedItems()` | All items currently borrowed | |
| `getOverdueItems(LocalDate today)` | Borrowed items whose due date is before `today` | |

(These three are included in the 30 marks above, at roughly 2 marks each when combined with the methods listed.)

---

## Section D: Sorting (5 marks)

- Make `LibraryItem` implement `Comparable<LibraryItem>` so items sort **by title** alphabetically (case-insensitive).
- Create a separate `Comparator` class (a normal named class, not a lambda) that sorts items **by due date**. Items without a due date go last.
- Use `Collections.sort(...)` for both. Add menu options to display sorted lists.

---

## Section E: Menu / UI Layer (20 marks)

Create a `Main` class (and optionally a `LibraryMenu` class) containing all console input and output.

### Required menu

```
===== LIBRARY MANAGEMENT SYSTEM =====
1.  Add item
2.  Remove item
3.  Search items
4.  Register member
5.  View member's borrowed items
6.  Borrow item
7.  Return item
8.  List available items
9.  List borrowed items
10. List overdue items
11. Pay member fees
12. List all items sorted by title
0.  Exit
Choose an option:
```

### Requirements

- Catch every custom exception and print a friendly message. The program must keep running.
- Handle non-numeric input where a number is expected (catch `NumberFormatException` or `InputMismatchException`).
- Handle out-of-range menu choices.
- Handle empty strings and blank IDs.
- Handle an invalid date format if you ask the user to enter dates.
- To make testing overdue items possible, add a hidden or clearly labelled option that lets the tester **enter a custom "today" date** for the session.

---

## Section F: Code Quality (10 marks)

- Sensible class, method, and variable names that follow Java conventions [2]
- All fields private, and encapsulation respected [2]
- No duplicated logic. Shared behaviour lives in the abstract class. [2]
- Sample data is loaded at startup so the program can be tested immediately (at least 3 books, 2 DVDs, 2 magazines, 2 members) [2]
- Brief comments explaining *why*, not *what*, where the logic is not obvious [2]

---

## Required Test Scenarios

Before you consider the project finished, make sure you can demonstrate each of these:

1. Borrow a book, then try to borrow the same book with another member. It should be rejected.
2. Borrow 3 items with one member, then try a 4th. It should be rejected.
3. Return a book 5 days late. The fee should be $2.50.
4. Return a DVD 3 days late. The fee should be $3.00.
5. Give a member more than $5 in fees. They should be blocked from borrowing until they pay.
6. Try to add two items with the same ID. The second should be rejected.
7. Try to remove an item that is currently borrowed. It should be rejected.
8. Search for `"the"` and get all items with "the" in the title (any case).
9. Type letters where a number is expected. The program must not crash.
10. Set the session date forward and confirm overdue items appear in the overdue report.

---

## Suggested Build Order

1. `LibraryItem` and subclasses, tested in a temporary `main`
2. `Member`
3. Custom exceptions
4. `Library` class, tested in a temporary `main`
5. Menu layer
6. Sorting and polish

Do not start the menu until the `Library` class works.

---

## Self-Check Questions

Answer these honestly when you finish:

- Could you add an `AudioBook` class by writing **one new class** and changing almost nothing else?
- Is there any `instanceof` chain in your code? If so, polymorphism should probably replace it.
- Does `Library` know anything about the console?
- Would your `equals` and `hashCode` still work correctly if two different `Book` objects had the same ID?
- What happens if the user presses Enter on an empty line at every prompt?

---

## Bonus Section (20 marks)

Attempt these only after everything above works.

| Bonus | Marks |
|---|---|
| Replace the `borrowed` boolean with an `enum ItemStatus { AVAILABLE, BORROWED, RESERVED }` | 4 |
| Add a waitlist per item using a `Queue` (`LinkedList`). When an item is returned, the next member is notified. | 6 |
| Keep a transaction history per member (borrow/return, date, fee) | 4 |
| Save and load all data to and from a file when the program exits and starts | 6 |

---

## Marking Summary

| Section | Marks |
|---|---|
| A. Model layer | 25 |
| B. Custom exceptions | 10 |
| C. Library class | 30 |
| D. Sorting | 5 |
| E. Menu / UI | 20 |
| F. Code quality | 10 |
| **Total** | **100** |
| Bonus | +20 |

*(Section totals are approximate guides. Use them to see where your effort is most valuable.)*

---

## Submission

When you are done, paste your code (or each class separately) and ask for a review. If you get stuck, ask for a **hint** on the specific section rather than a full solution. Good luck!