package Model;
import java.time.LocalDate;

public class Book extends LibraryItem{
    
    private String author;
    
    public Book(String id, String title, boolean borrowed, LocalDate dueDate, String author){
        super(id,title,borrowed,dueDate);
        this.author=author;
    }
    @Override 
    public int getLoanPeriodDays(){
        return 14;
    }
    @Override 
    public double getDailyLateFee(){
        return 0.50;
    }
   @Override 
   public boolean matches(String keyword){
    if(keyword==null) return false;
    return super.matches(keyword) || (author!=null && author.toLowerCase().contains(keyword.toLowerCase()));
   }
   @Override 
   public String toString(){
    return super.toString() + " author: "+ author + " ] ";
   }
}