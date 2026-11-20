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
 
    /**
     * @param args
     * @throws ItemNotAvailable
     * @throws ItemNotFoundException
     * @throws MemberNotFoundException
     * @throws MemberLimitExceededException
     * @throws DuplicateMemberException
     * @throws NoDateException
     */
    public static void main(String[] args) throws ItemNotAvailable, ItemNotFoundException, MemberNotFoundException, MemberLimitExceededException, DuplicateMemberException, NoDateException {
        Scanner scanner=new Scanner(System.in);
        Library library= new Library();
        LocalDate date;
        Member member=new Member(null, null);
        ArrayList<LibraryItem> list=new ArrayList<>();
        boolean toContinue=true;

        while(toContinue){
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
                 date=readDate(scanner,"Enter the Date You Borrowed the Item in the Format YYYY-MM-DD");
                borrowedItems(memberId, itemID, date, library);
                System.out.println(" You have borrowed item with Id: " +itemID);
                break;
            case 7:
                itemID=StringInputs(scanner," Enter the ItemID: ");
                memberId=StringInputs(scanner, "Enter the member's ID: ");
                date=LocalDate.now();
                System.out.println("$ "+ library.returnItem(memberId, itemID, date));
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
                // String date1=StringInputs(scanner, " Enter Date in Format YYYY-MM-DD: ");
                System.out.println("Find items that are overdue relative to a specific date.");
                date=readDate(scanner,"Enter reference date (YYYY-MM-DD): ");
                System.out.println(" Below are the items in the library that are overdue ");
                System.out.println(library.getOverdueItems(date));
                break;
            case 11:
                itemID=StringInputs(scanner," Enter the ItemID: ");
                memberId=StringInputs(scanner, "Enter the member's ID: ");
                System.out.println(" Enter  amount you want to pay: ");
                double amount= scanner.nextDouble();
                library.payingForItemsReturnedLate(memberId, itemID, amount);
                break;
            case 12:
                System.out.println(" All items sorted by title ");
                list =library.getAvailableItems();

                Collections.sort(list);
                System.out.println(list);
                break;
            case 0:
                toContinue=false;
                System.out.println("Exiting ....");
                break;
            default:
                System.out.println("\nPLEASE ENTER A VALID OPTION WITHIN 0-12 !");
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
        System.out.println("Press 0 to Exit");

      
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



    //Method for taking user input to populate data structures

    //Reading Boolean
    public static boolean readBoolean(Scanner scanner, String prompt){
        System.out.println(prompt);
        Boolean option=scanner.nextBoolean();
        return option;
    }
    //Reading Date
    public static LocalDate readDate(Scanner scanner,String prompt){
           System.out.println(prompt);
           String date=scanner.nextLine();
           LocalDate correctDate= LocalDate.parse(date);
           return correctDate;
       
        
    }
    //Reading String Inputs
    public static String StringInputs(Scanner scanner,String prompt){
        System.out.println(prompt);
        String input=scanner.nextLine().trim();
       
        
        return input;
    }

    //borrowing Items and setting the duedate
    public static void borrowedItems(String memberId, String itemId ,LocalDate date,Library library) throws    ItemNotAvailable, MemberNotFoundException, MemberLimitExceededException, ItemNotFoundException{
        
        // Library library= new Library();
        // memberId=StringInputs(scanner, " Enter the memberId ");
        // itemId=StringInputs(scanner, " Enter the ItemId: ");
        // date=readDate(scanner," Enter the Date ");

        library.borrowItem(memberId, itemId, date);
        

    }

    //Reading
    public static int readIntChoice(Scanner scanner,String prompt){
        System.out.println(prompt);
        
        
        while(true){
           
                if(scanner.hasNextInt()){
                    int option=scanner.nextInt();
                    scanner.nextLine();
                    return option;
                }else{
                    System.out.println("Enter a valid integer!");
                    scanner.nextLine();
                }
            
        }
        
    }

    //A helper method to populate the allow a user to create and populate the arraylist
  public static LibraryItem LibraryItemCreationHelper(Scanner scanner, String prompt){
    boolean subMenuLoop=true;
      int choice=readIntChoice(scanner,"\nChoose which Library item you want \n"+
            "\n1. DVD \n"+
            "\n2. Book \n"+
            "\n3. Magazine\n" );
    while(subMenuLoop){
        if(choice ==1 || choice ==2 || choice ==3){
            subMenuLoop=false;
            break;
        }else{
             System.out.println("\n Please enter a valid option between 1-3 ! \n");
             choice= readIntChoice(scanner,"\nPlease choose again which Library item you want \n"+
            "\n1. DVD \n"+
            "\n2. Book \n"+
            "\n3. Magazine\n" );
            
        }
        
    }
        String id=StringInputs(scanner, " Enter the Id: ");
        String title=StringInputs(scanner, " Enter the Title: ");
 
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