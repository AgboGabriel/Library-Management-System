package Model;
import java.util.ArrayList;
import java.util.Objects;

//A class for any Member that will be created in the Application

public class Member {
    private String memberId;
    private String name;
    private ArrayList<LibraryItem> borrowedItems;
    private double unpaidFees;

    public Member(String memberId, String name){
        this.memberId=memberId;
        this.name=name;
        // if(borrowedItems==null){
        //      borrowedItems=new ArrayList<>();
        // }else{
        //     this.borrowedItems=new ArrayList<>(borrowedItems);
        // }
        
        // this.unpaidFees=unpaidFees;
    }
    public String getMemberId(){
        return memberId;
    }

    public String getName(){
        return name;
    }
    public double getUnpaidFees(){
        return unpaidFees;
    }
  
   
    public void addToBorrowed(LibraryItem item){
        if(borrowedItems==null){
            borrowedItems=new ArrayList<>();
        }
        this.borrowedItems.add(item);
    }
    public ArrayList<LibraryItem> getBorrowedItems(){
        return new ArrayList<>(this.borrowedItems);
    }

    //Removing items;
    public void removeItems(int index){
        this.borrowedItems.remove(index);
    }
    public void removeFirst(){
        this.borrowedItems.removeFirst();
    }
    public void removeLast(){
        this.borrowedItems.removeLast();
    }

    //PayFees, add Fees
    public double addFees(double fees){
        unpaidFees+=fees;
        return unpaidFees;
    }
    public double payFees(double fees){
        unpaidFees-=fees;
        if(unpaidFees<0){
            double balance= unpaidFees *-1;
        }
        return unpaidFees;
    }
    public int getCount(){
        if(borrowedItems==null){
            return 0;
        }
        return borrowedItems.size();
    }
     public void removeItem(LibraryItem item) {
      for(LibraryItem it: borrowedItems){
        if(it.getID().equals(item.getID())){
            borrowedItems.remove(item);
            return;
        }

      }
    }
    
    //Overriding equals, hashCode and toString
    @Override 
    public boolean equals(Object obj){
        if(this==obj)return true;

        if(obj==null && getClass()!=obj.getClass()) return false;

       Member Other=(Member) obj;
       return Objects.equals(this.memberId, Other.memberId);
    }
    @Override 
    public int hashCode(){
        return Objects.hash(this.memberId);
    }
    @Override 
    public String toString(){
        return "Member [ID: "+ memberId+" , Name: " + name + " , Items Borrowed: " +
        getBorrowedItems() + ", Unpaid Fees: $ "+ unpaidFees + "]";
    }
   
}
