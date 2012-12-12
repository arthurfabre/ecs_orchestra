package Orchestra;

// Public class Trumpeter
public class Trumpeter extends Musician {
    
    // Constructor takes a SoundSystem and a seat as parameters.
    public Trumpeter(SoundSystem soundSystem, Integer seat) {
        // Pass the parameters on the to the Musician constructor along with trumpeter specific properties such as InstrumentID, louad and soft volumes.
        super(57, 100, 200, soundSystem, seat);
    }
}