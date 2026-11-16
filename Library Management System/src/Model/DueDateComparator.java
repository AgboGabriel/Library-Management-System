package Model;

import java.util.Comparator;

public class DueDateComparator implements  Comparator<LibraryItem>{
  public int compare(LibraryItem itemA,LibraryItem itemB){
    return itemA.getDueDate().compareTo(itemB.getDueDate());
  }

}
