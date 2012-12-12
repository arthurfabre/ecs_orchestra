package Orchestra;

// Public class Orchestra. Used to keep track of the musicians by seating them in an OrchestraList (extends ArayList)
public class Orchestra {
    // Create the OrchestraList to seat the musicians
    protected OrchestraList arrayOfSeats;
    // Create a constant to store the maximum number of musicians supported.
    protected final int MAX_MUSICIANS = 16;
    
    // Orchestra constructor. Create an OrchestraList with a set maximum number of musicians (see OrchestraList for further details)
    public Orchestra() {
        arrayOfSeats = new OrchestraList(MAX_MUSICIANS);
    }
    
    // getArrayOfMusicians method. Returns the OrchestraList used to seat the musicians.
    public OrchestraList getArrayOfMusicians() {
        return arrayOfSeats;
    }
    
    // addMusician method. Calls the overloaded method with the nextAvailableSeat in the orchestra as a parameter. Throws an excpetion
    // if the orchestra is full.
    public void addMusician(Musician musician) throws Exception {
        addMusician(musician, getNextAvailableSeat());
    }
    // Overloaded addMusician method. Is used to addMusicians to the orchestra.
    public Musician addMusician(Musician musician, int seatNumber) throws Exception {
        // If the muscian is a LeadViolinist, set his seatNumber to be 1. Use setSeat to update the musicians own seat.
        if (musician instanceof LeadViolinist) {
            seatNumber = 1;
            musician.setSeat(seatNumber);
        }
        // Check if the orchestra already contains the specific musician. If not, replace whatever was sitting in the seat and return it.
        // Else throw a new exception.
        if (!arrayOfSeats.contains(musician)) {
            // seatNumber - 1 to convert from seats (first = 1) to index (first = 0).
            return arrayOfSeats.set((seatNumber - 1), musician);
        } else {
            throw new Exception("Musician is already in the orchestra!");
        }
    }
    
    // getSize method. Returns the number of musicians currently in the orchestra.
    public int getSize() {
        return arrayOfSeats.size();
    }
    
    // getMusician. Returns the musician sitting in the specifed seat.
    public Musician getMusician(int seatNumber) {
        // seatNumber - 1 to convert from seats (first = 1) to index (first = 0).
        return arrayOfSeats.get((seatNumber - 1));
    }
    
    // getNextAvailableSeat method. returns the next seat that is fre (ie not occupied by a musician.
    public int getNextAvailableSeat() {
        // seatNumber + 1 to convert from index (first = 0) to seats (first = 1).
        return (arrayOfSeats.getNextAvailableIndex() + 1);
    }
}