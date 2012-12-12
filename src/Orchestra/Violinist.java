package Orchestra;

// Public class Violinist, extends Musician
public class Violinist extends Musician {
    
    // Constructor takes a SoundSystem and a seat as parameters.
    public Violinist(SoundSystem soundSystem, Integer seat) {
        // Pass the parameters on the to the Musician constructor along with violiist specific properties such as InstrumentID, louad and soft volumes.
        super(41, 50, 100, soundSystem, seat);
    }
}