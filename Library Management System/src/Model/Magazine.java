package Model;

import java.time.LocalDate;

/**
 * Magazine
 */
public class Magazine extends LibraryItem {
   int issueNumber;
    public Magazine(String id,String title, boolean borrowed, LocalDate dueDate, int issueNumber){
        super(id, title, borrowed, dueDate);
        this.issueNumber=issueNumber;
    }
    @Override 
    public int getLoanPeriodDays(){
        return 3;
    }
    public double getDailyLateFee(){
        return 0.25;
    }
    @Override 
    public String toString(){
        return super.toString() + " issueNumber: "+ issueNumber + " ] ";
    }

}