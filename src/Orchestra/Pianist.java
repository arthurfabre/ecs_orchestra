package Orchestra;

// Public class pianist extends Musician
public class Pianist extends Musician {
    
    // Constructor takes a SoundSystem and a seat as paremeters.
    public Pianist(SoundSystem soundSystem, Integer seat) {
        // Pass the parameters on the to the Musician constructor along with pianist specific properties such as InstrumentID, louad and soft volumes.
        super(1, 75, 100, soundSystem, seat);
    }
}