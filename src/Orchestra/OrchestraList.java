package Orchestra;

import java.util.ArrayList;
import java.util.Iterator;

public class OrchestraList extends ArrayList<Musician> implements Iterable<Musician> {
    private final int MAX_SIZE = 16;
    
    public class OrchestraListIterator implements Iterator<Musician> {
        private OrchestraList orchestra;
        private int currentPosition;
        
        public OrchestraListIterator(OrchestraList orchestra) {
            this.orchestra = orchestra;
            currentPosition = -1;
        }
        
        public boolean hasNext() {
            boolean hasNext = false;
            for (Musician musician : orchestra.subList(currentPosition + 1, MAX_SIZE - 1)) {
                if (musician != null) {
                    hasNext = true;
                }
            }
            return hasNext;
        }
        
        public Musician next() {
            currentPosition ++;
            while (orchestra.get(currentPosition) == null) {
                currentPosition ++;
            }
            return orchestra.get(currentPosition);
        }
        
        public void remove() {
            
        }
    } 
    
    public OrchestraList(int initialCapacity){
        super(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            super.add(null);
        }
    }
    
    public void add(int index, Musician musician) throws IndexOutOfBoundsException {
        if (super.size() <= MAX_SIZE) {
            super.set(index, musician);
        } else {
            throw new IndexOutOfBoundsException("Orchestra Full");
        }
    }
    
    public Musician set(int index, Musician musician) throws IndexOutOfBoundsException {
        Musician previousMusician = this.get(index);
        this.add(index, musician);
        return previousMusician;
    }
    /*
    public boolean add(Musician musician) throws IndexOutOfBoundsException {
        if (super.size() <= MAX_SIZE) {
            super.add(musician);
        } else {
            throw new IndexOutOfBoundsException("Orchestra Full");
        }
        return true;
    }*/
    
    public Iterator iterator() {
        return new OrchestraListIterator(this);
    }
    
    public boolean containsGeneric(String musicianType){
        boolean containsGeneric = false;
        for (Musician musician : this) {
            if (musician.getClass().getSimpleName().equalsIgnoreCase(musicianType)) {
                containsGeneric = true;
            }
        }
        return containsGeneric;
    }
    
    public Musician getGeneric(String musicianType) {
        for (Musician musician : this) {
            if (musician.getClass().getSimpleName().equalsIgnoreCase(musicianType)) {
                return musician;
            }
        }
        return null;
    }
    
    public int size() {
        int size = 0;
        for (Musician musician : this) {
            size ++; 
        }
        return size;
    }
    
    public int getNextAvailableIndex() {
        return this.indexOf(null);
    }
}