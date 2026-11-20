package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class LibraryItem implements Searchable,Comparable<LibraryItem>{
    private String id;
    private String title;
    private boolean borrowed;
    private LocalDate dueDate;

    LibraryItem(){

    }
    LibraryItem(String id, String title, boolean borrowed, LocalDate dueDate){
        setID(id);
        setTitle(title);
        setBorrowed(borrowed);
        setDueDate(dueDate);
    }
    void setTitle(String title){
        this.title=title;
    }
    void setID(String id){
        this.id=id;
    }
    void setBorrowed(boolean borrowed){
        this.borrowed=borrowed;
    }
    void setDueDate(LocalDate dueDate){
        this.dueDate=dueDate;
    }
    String getTitle(){
        return title;
    }
    String getID(){
        return id;
    }
    boolean getBorrowed(){
        return borrowed;
    }
    LocalDate  getDueDate(){
        return dueDate;
    }
    @Override 
    public int compareTo(LibraryItem other){
        return this.title.compareToIgnoreCase(other.title);
    }
    @Override 
    public boolean equals(Object obj){

        if(this==obj)return true;

        if(obj==null || getClass()!=obj.getClass()) return false;

        LibraryItem other=(LibraryItem) obj;
        return Objects.equals(this.id, other.id);

    }
    @Override 
    public int hashCode(){
        return Objects.hash(this.id);
    }
    @Override 
    public boolean matches(String keyword){
        if(keyword==null || title==null)return false;
        return title.toLowerCase().contains(keyword.toLowerCase());
    }
    
    public abstract int getLoanPeriodDays();
        // LocalDate CurrentDate= LocalDate.now();
        // Long DaysOverdue=dueDate.until(CurrentDate, ChronoUnit.DAYS);
        // return  DaysOverdue;
    
    public abstract double getDailyLateFee();
        // return 5 * getLoanPeriodDays();
    
    double calculateLateFee(LocalDate returndate){
        // int LoanPeriodDays=getLoanPeriodDays();
        double dailyLate=getDailyLateFee();
        Long DaysOverdue=dueDate.until(returndate, ChronoUnit.DAYS);
        if(DaysOverdue==0){
            return 0;
        }
    
        else{
            return dailyLate*DaysOverdue;
        }

    }
    @Override 
    public String toString(){
        return " [id: "+ id + ", title: " + title + ", borrowed: "+ borrowed + ", dueDate: " + dueDate+ "  ";
    }
}