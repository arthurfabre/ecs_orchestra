package Orchestra;
import java.util.ArrayList;

public class Orchestra {
    ArrayList<Musician> arrayOfSeats;
        
    public Orchestra() {
        arrayOfSeats = new ArrayList<>(15);
    }
    
    public ArrayList<Musician> getArrayOfMusicians() {
        return arrayOfSeats;
    }
    
    private void addMusician(Musician musician) {
        this.arrayOfSeats.add(musician);
    }
    
    private Musician addMusician(Musician musician, int seatNumber) {
        try {
            return arrayOfSeats.set((seatNumber - 1), musician);
        } catch (IndexOutOfBoundsException e) {
            arrayOfSeats.add((seatNumber - 1), musician);
            return null;
        }
    }
    
    public int getSize() {
        return arrayOfSeats.size();
    }
    
    public Musician getMusician(int seatNumber) {
        return arrayOfSeats.get((seatNumber - 1));
    }
    
    public int getNextAvailableSeat() {
        return (getSize() + 1);
    }
    
    public void seatMusician(Musician musician, int seatNumber) {
        if (musician instanceof LeadViolin) {
            seatNumber = 1;
            musician.setSeat(seatNumber);
        }
        
        Musician previousMusician = addMusician(musician, seatNumber);
        if (previousMusician != null && getSize() < 15) {
            addMusician(previousMusician);
            previousMusician.setSeat(arrayOfSeats.indexOf(previousMusician));
        }
    }
}