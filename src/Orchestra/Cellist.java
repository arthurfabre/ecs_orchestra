package Orchestra;

// Public class Cellist, extends Musician
public class Cellist extends Musician {
    
    // Constructor takes a SoundSystem and a seat as parameters.
    public Cellist(SoundSystem soundSystem, Integer seat) {
        // Pass the parameters on the to the Musician constructor along with cellist specific properties such as InstrumentID, louad and soft volumes.
        super(43, 50, 100, soundSystem, seat);
    }
}