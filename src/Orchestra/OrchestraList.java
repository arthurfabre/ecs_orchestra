package Orchestra;

import java.util.ArrayList;
import java.util.Iterator;

// public class OrchestraList extends ArrayList.
// NOTE: This class was created as the spec required us to use an arrayList, even though an array would have been a much easier solution (fixed size and
// ability to add objects anywhere). These properties where implemented to ArrayList through OrchestraList.
public class OrchestraList extends ArrayList<Musician> implements Iterable<Musician> {
    // Set the maximum 
    private int capacity;
    
    // Create a subclass to override the default itterator. The array if filled by default with null elemnts so we can add elements wherver we wish, 
    // iterating over the array must however not return these null values.
    public class OrchestraListIterator implements Iterator<Musician> {
        // Define instance variables, such as the OrchestraList to work on and the current position.
        private OrchestraList orchestra;
        private int currentPosition;
        
        // OrchestraListIterator constructor, takes an OrchestraList as a parameter and sets it to be an instance variable.
        public OrchestraListIterator(OrchestraList orchestra) {
            this.orchestra = orchestra;
            // Set the current position to be -1;
            currentPosition = -1;
        }
        
        // hasNext method(). Returns true only if the orchestraList contains a next Musician.
        public boolean hasNext() {
            boolean hasNext = false;
            // Loop over the ArrayList and only set hasNext to be true if we find a non null musician starting at the current position.
            for (Musician musician : orchestra.subList(currentPosition + 1, capacity - 1)) {
                if (musician != null) {
                    hasNext = true;
                }
            }
            return hasNext;
        }
        
        // next method(). Retunrs the next non null musician in the array.
        public Musician next() {
            // increase current position to get to the next musician.
            currentPosition ++;
            while (orchestra.get(currentPosition) == null) {
                // keep increasing currentPosition until we reach a non-null musician.
                currentPosition ++;
            }
            return orchestra.get(currentPosition);
        }
        
        public void remove() {
            
        }
    } 
    
    // OrchestraList Constructor. Takes a int capacity as a parameter, which is the maximum number of elements this orchestraList will hold.
    public OrchestraList(int capacity){
        super(capacity);
        this.capacity = capacity;
        // Loop over the array and fill it with null elements.
        for (int i = 0; i < capacity; i++) {
            super.add(null);
        }
    }
    
    // addMusician method. Takes an index and a musician as a parameter,throws and Exception if the Orchestra is full. 
    public void add(int index, Musician musician) throws IndexOutOfBoundsException {
        // If the orchestra isn't full, add the musician in the specifed seat
        if (size() <= capacity) {
            super.set(index, musician);
        // Otherwise throw an exception.
        } else {
            throw new IndexOutOfBoundsException("Orchestra Full");
        }
    }
    
    public Musician set(int index, Musician musician) throws IndexOutOfBoundsException {
        Musician previousMusician = this.get(index);
        this.add(index, musician);
        return previousMusician;
    }
    
    public Iterator iterator() {
        return new OrchestraListIterator(this);
    }
    
    // containsGeneric method. Takes a classname as a parameter and checks to make sure that at least one musician in the OrchestraList has the 
    // same simple anme as the input parameter.
    public boolean containsGeneric(String className){
        boolean containsGeneric = false;
        for (Musician musician : this) {
            if (musician.getClass().getSimpleName().equalsIgnoreCase(className)) {
                containsGeneric = true;
            }
        }
        return containsGeneric;
    }
    
    //getGeneric method. Takes a className as a parameter and returns the last musician of the same type in the OrchestraList.
    public Musician getGeneric(String className) {
        Musician returnMusician = null;
        // iterate over the OrchestraList, if a musician is found with the same simple class name as the className parameter,
        // return that musician.
        for (Musician musician : this) {
            if (musician.getClass().getSimpleName().equalsIgnoreCase(className)) {
                returnMusician = musician;
            }
        }
        return returnMusician;
    }
    
    // size method. Returns the number of non-null musicians in the OrchesraList using the over-ridden iterator.
    public int size() {
        int size = 0;
        // iterate over the OrchestraList
        for (Musician musician : this) {
            // For every musician found, add 1 to the size variable.
            size ++; 
        }
        return size;
    }
    
    // getNextAvailableIndex method. Returns the index of the fisrt null spot in the OrchestraList.
    public int getNextAvailableIndex() {
        return this.indexOf(null);
    }
}