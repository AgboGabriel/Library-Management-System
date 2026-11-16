import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import Exceptions.DuplicateMemberException;
import Exceptions.ItemNotAvailable;
import Exceptions.ItemNotFoundException;
import Exceptions.MemberLimitExceededException;
import Exceptions.MemberNotFoundException;
import Exceptions.NoDateException;
import Model.*;

public class Main {
 


    public static void main(String[] args) throws ItemNotAvailable, ItemNotFoundException, MemberNotFoundException, MemberLimitExceededException, DuplicateMemberException, NoDateException {
        Scanner scanner=new Scanner(System.in);
        Library library= new Library();
        Member member=new Member(null, null);
        ArrayList<LibraryItem> list=new ArrayList<>();

        while(true){
            displayMenu(scanner);
            int choice=scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                addItems(scanner,library);
                System.out.println("Adding items ...");
                break;
            case 2:
                String itemId=StringInputs(scanner, "Enter the ID of the Item you want to remove");
                System.out.println("Removing items ...");
                library.removeItem(itemId);
                break;
            case 3:
                 String searchtitle=StringInputs(scanner, " Enter the title of the item");
                 list=library.searchItems(searchtitle);
                 System.out.print(list);
                break;
            case 4:
                member=registeringMember(scanner, " Enter member details following the prompts below", member);
                library.registerMember(member);
                System.out.println("Member Registered");
                break;
            case 5:
                System.out.println("The are the borrowed Items: ");
                System.out.print(library.getBorrowedItems());
                break;
            case 6:
                String memberId=StringInputs(scanner, "Enter the Member ID: ");
                String itemID=StringInputs(scanner," Enter the ItemID: ");
                LocalDate date=LocalDate.now();
                library.borrowItem(memberId,itemID, date);
                System.out.println(" Borrowing Items Menu: ");
                break;
            case 7:
                itemID=StringInputs(scanner," Enter the ItemID: ");
                memberId=StringInputs(scanner, "Enter the member's ID: ");
                date=LocalDate.now();
                System.out.println(library.returnItem(memberId, itemID, date));

                break;
            case 8:
                System.out.println(" Below are the items in the library that are available(Have not been borrowed) ");
                System.out.println(library.getAvailableItems());
                break;
            case 9:
                System.out.println(" Below are the items in the library that are borrowed ");
                System.out.println(library.getBorrowedItems());
                break;
            case 10:
                System.out.println(" Below are the items in the library that are overdue ");
                date=LocalDate.now();
                System.out.println(library.getOverdueItems(date));
                break;
            case 11:
                System.out.println(" Enter  amount you want to pay: ");
                double amount= scanner.nextDouble();
                member.payFees(amount);
                break;
            case 12:
                System.out.println(" All items sorted by title ");
                list =library.getAvailableItems();

                Collections.sort(list);
                System.out.println(list);
            default:
                break;
        }
        }
          
        
       
        

   

    }  
    public static void displayMenu(Scanner scanner) {
        System.out.println("================================================");
        System.out.println("======== LIBRARY MANAGEMENT SYSTEM ======");
        System.out.println("================================================");
        System.out.println("Press 1 to Add Items to the Library");
        System.out.println("Press 2 to Remove Items From the Library");
        System.out.println("Press 3 to Search for Items ");
        System.out.println("Press 4 to Register member ");
        System.out.println("Press 5 to View a member's borrowed Items");
        System.out.println("Press 6 to Borrow Items ");
        System.out.println("Press 7 to Return Items to the Library");
        System.out.println("Press 8 to List available Items in the Library");
        System.out.println("Press 9 to List borrowed Items ");
        System.out.println("Press 10 to List Overdue Items");
        System.out.println("Press 11 to Pay member Fees");
        System.out.println("Press 12 to List all items sorted by title");

      
    }
    public static void addItems(Scanner scanner,Library library) throws ItemNotAvailable{
        LibraryItem item = LibraryItemCreationHelper(scanner, " Enter the Items to Add: ");
        library.addItem(item);
    }
  
    //Registering member
    public static Member registeringMember(Scanner scanner,String prompt, Member member){
        String memberId= StringInputs(scanner, " Enter the memberId: ");
        String name= StringInputs(scanner, " Enter member name: ");
       
        //On Friday (November 13th removed  this item because the constructor was adjusted to recieve only Name and ID)
        // LibraryItem item= LibraryItemCreationHelper(scanner, prompt);
        
        // list.add(item);
        member=new Member(memberId, name);
        return member;
    }
    //

    //Method for taking user input to populate data structures

    //Reading Boolean
    public static boolean readBoolean(Scanner scanner, String prompt){
        System.out.println(prompt);
        Boolean option=scanner.nextBoolean();
        return option;
    }
    //Reading Date
    public static LocalDate readDate(Scanner scanner){
           String date=StringInputs(scanner, " Enter Date in Format YYYY-MM-DD: ");
           LocalDate correctDate= LocalDate.parse(date);
           return correctDate;
       
        
    }
    //Reading String Inputs
    public static String StringInputs(Scanner scanner,String prompt){
        System.out.println(prompt);
        String input=scanner.nextLine().trim();
        
        return input;
    }

    public void borrowedItems(Scanner scanner,String prompt) throws    ItemNotAvailable, MemberNotFoundException, MemberLimitExceededException, ItemNotFoundException{
        Library library= new Library();
        String memberId=StringInputs(scanner, " Enter the memberId ");
        String itemId=StringInputs(scanner, " Enter the ItemId: ");
        LocalDate date=readDate(scanner);

        library.borrowItem(memberId, itemId, date);
      
    
    }

    //Reading
    public static int readIntChoice(Scanner scanner,String prompt){
        System.out.println(prompt);
        int option=scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    //A helper method to populate the allow a user to create and populate the arraylist
  public static LibraryItem LibraryItemCreationHelper(Scanner scanner, String prompt){
      int choice=readIntChoice(scanner,"\nChoose which Library item you want \n"+
            "\n1. DVD \n"+
            "\n2. Book \n"+
            "\n3. Magazine\n" );
    
        String id=StringInputs(scanner, " Enter the Id: ");
        String title=StringInputs(scanner, " Enter the Title: ");
        // boolean borrowed=readBoolean(scanner, " is item borrowed? true/false ");
        // LocalDate date=readDate(scanner);
       
       
      
        switch (choice) {
            case 1:
                String director=StringInputs(scanner, " Enter the director's name: ");
                System.out.println(" Item is a DVD ");
                DVD dvd=new DVD(id, title, false, null, director);
             return(dvd);
            case 2:
                String author=StringInputs(scanner, " Enter the author's name: ");
                System.out.println(" Item is a Book ");
                Book book=new Book(id, title, false, null, author);
                return (book);
            case 3:
                int issueNumber=readIntChoice(scanner, " Enter the issueNumber: ");
                System.out.println("Item is a Magazine ");
                Magazine magazine=new Magazine(id, title, false, null, issueNumber);
                return(magazine);
            default:
                return null;
                
        }
       
  }

}