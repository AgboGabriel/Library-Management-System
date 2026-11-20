package Model;

import java.time.LocalDate;

public class DVD extends LibraryItem{
    String director;
    public DVD(String id,String title, boolean borrowed, LocalDate dueDate,String director){
        super(id,title,borrowed,dueDate);
        this.director=director;
    }
    @Override 
    //This shows the amount of days user can borrow a specific book without paying late fee
    public int getLoanPeriodDays(){
        return 7;
    }
    @Override 
    public double getDailyLateFee(){
        return 1;
    }
    @Override 
    public boolean matches(String keyword){
        if(keyword==null)return false;
        return super.matches(keyword) || (director.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override 
    public String toString(){
        return super.toString() + " director: "+director + " ] ";
    }

     
}