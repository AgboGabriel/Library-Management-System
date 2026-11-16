package Model;

import java.util.HashMap;
import Exceptions.DuplicateMemberException;
import Exceptions.ItemNotAvailable;
import Exceptions.ItemNotFoundException;
import Exceptions.MemberLimitExceededException;
import Exceptions.MemberNotFoundException;
import Exceptions.NoDateException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Library {
    private ArrayList<LibraryItem> list;
    private HashMap<String,Member> map;

    public Library(){
        this.list=new ArrayList<>();
        this.map=new HashMap<>();
    }
  
    public void addItem(LibraryItem item) throws ItemNotAvailable{
        if(this.list.contains(item)) {
            throw new ItemNotAvailable("Item: "+ item+ " already exists hence it cannot be added again");
        }
        list.add(item);
    }
    public void removeItem(String id) throws ItemNotFoundException, ItemNotAvailable{
        for(LibraryItem item: list){
            if(item.getID().equals(id)){
                if(item.getBorrowed()==true){
                    throw new ItemNotAvailable("Cannot remove item since it is borrowed");
                }
              //i check if the id exist, and check if the item is borrowed or not
                list.remove(item);
                return;
            }
            
        }
                //else i throw an exception
            throw new ItemNotFoundException("Item with id: "+ id +" Not Found");
            
    }
    public void registerMember(Member m) throws DuplicateMemberException{
        if(map.containsKey(m.getMemberId())){
            throw new DuplicateMemberException(m.getName() +" with id: "+ m.getMemberId() + " Already exists");
        }
       map.put(m.getMemberId(),m);
       System.out.println(map.keySet());
    }
    public ArrayList<LibraryItem> searchItems(String keyword){
        ArrayList<LibraryItem> LibItems=new ArrayList<>(); 
        for(LibraryItem item: list){
            if(item.matches(keyword)){
                LibItems.add(item);
            }
            
        }
        return LibItems;
    }
    public LibraryItem findItemById(String id) throws ItemNotFoundException{
        LibraryItem found=null;
        for(LibraryItem item: list){
            if(item.getID().equals(id)){
                found=item;
                return found;
            }
        }
        throw new ItemNotFoundException("Item with id: "+ id+ "Not Found");
        
    }
    
    public LibraryItem borrowItem(String memberId, String itemId, LocalDate today) throws MemberNotFoundException, ItemNotAvailable, MemberLimitExceededException, ItemNotFoundException{
        Member person= map.get(memberId);
        
        if(person==null){
            throw new MemberNotFoundException("Member with id: "+ memberId + " does not exist");
        }
        
        if(person.getUnpaidFees()>5.00){
            throw new MemberLimitExceededException("You have exceeded your limit- You have an Unpaid fee of over  5 dollars. Actual outstanding amont: "+person.getUnpaidFees());
        }

        LibraryItem item=findItemById(itemId);
        
        if(item.getBorrowed()){

            throw new ItemNotAvailable("Item: "+item +" is not available as it has already been borrowed");
        }
      if(person.getCount()>=3){
        throw new MemberLimitExceededException("Member limit exceeded. A member must have at most 3 items. You have: "+person.getCount());
      }
       person.addToBorrowed(item);
      item.setBorrowed(true);
     
      item.setDueDate(today.plusDays(item.getLoanPeriodDays()));
      return item;
   
    }
   
    public double returnItem(String memberId, String itemId,LocalDate today) throws ItemNotFoundException, MemberNotFoundException{
        LibraryItem item=findItemById(itemId);
        
        Member person=map.get(memberId);
        if(person==null){
            throw new MemberNotFoundException("Member with id: "+ memberId+" Not Found");
        }
        person.removeItem(item);
         double chargeFee=item.calculateLateFee(today);
         person.addFees(chargeFee);
        item.setBorrowed(false);
        return chargeFee;
    }

    public ArrayList<LibraryItem> getAvailableItems() {
        ArrayList<LibraryItem> container= new ArrayList<>();
        for(LibraryItem item:list){
           if(item.getBorrowed()==false){
             container.add(item);
           }
        }
        return container;
    }

    public ArrayList<LibraryItem> getBorrowedItems(){
        ArrayList<LibraryItem> container=new ArrayList<>();
        for(LibraryItem item: list){
            if(item.getBorrowed()){
                container.add(item);
            }
        }
        return container;
    }

    public ArrayList<LibraryItem> getOverdueItems(LocalDate today) throws NoDateException{
         ArrayList<LibraryItem> container=new ArrayList<>();
        for(LibraryItem item: list){
        if(item.getDueDate()==null){
            throw new NoDateException("No date has been entered for due date");
        }
        if(item.getBorrowed()){
            if(item.getDueDate().isBefore(today)){
                container.add(item);
            }

        }
            }
            return container;
        }

    
    }
