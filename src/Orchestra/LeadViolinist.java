package Orchestra;

// Public class LeadViolinist extends Violinist.
public class LeadViolinist extends Violinist {
    // Declare all the instance variables used by the LeadViolinist to comunicate with the orchestra. All communication with the conductor is done through
    // exceptions.
    protected Orchestra orchestra;
    
    // Default LeadViolinist constructor. Accpets the same parameters as every other musician in order to use reflection to dynamically create musicians at run-time.
    public LeadViolinist(SoundSystem soundSystem, Integer seat) {
        super(soundSystem, seat);
    }
    
    // Overloaded LeadViolinst constructor, takes a suplemental paramter, an Orchestra in order to conform with the spec.
    public LeadViolinist(SoundSystem soundSystem, Integer seat, Orchestra orchestra) {
        super(soundSystem, seat);
        this.orchestra = orchestra;
    }
    
    // init() method used to set additional parameters that aren't passsed in the defualt constructor. 
    public void init(Orchestra orchestra) {
        this.orchestra = orchestra;
    }
    
    // playNextNote method, plays the violinists notes and instructs all the other musicians to subsequently play.
    public void playNextNote() throws MusicianEndOfLifeException {
        // First play the LeadViolinist's own note.
        // NOTE: It's necessary to do this as if we overrode the LeadViolinist's play nextNote method to simply loop over every musician in the 
        // orchestra we'd end up with an endless loop.
        super.playNextNote();
        // Call the play method to instruct all the other musicians to play their respective notes.
        play();
    }
    
    // play method. Instructs all the musicians to play their next note.
    public void play() throws MusicianEndOfLifeException {
        // For every musicians in the orchestra that isn't a lead violinst, instruct the musician to play their next note.
        for(Musician musician : orchestra.getArrayOfMusicians()) {
            if (!(musician instanceof LeadViolinist)) {
                musician.playNextNote();
            }
        }
    }
}