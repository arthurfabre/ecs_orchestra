package Orchestra;

// Abstract class Musician
public abstract class Musician {
    // Declare all the instace variables used to store properties of the musician.
    protected int instrumentID;
    protected int softVolume;
    protected int loudVolume;
    protected SoundSystem soundSystem;
    protected int seat;
    // Maximum amount of notes a musician can play before he must be replaced by a new one.
    protected final int MAX_NOTES = 100;
    // Used to store the notes the musician has been read.
    protected int[] arrayOfNotes;
    // Used to store previous and next notes that need to be played
    protected int nextNote = 0;
    protected int previousNote;
    // Store the volume the musican is currently playing at.
    protected int setVolume;
        
    // Musician constructor. Takes an Instrument ID, a loud volume, a soft volume, a SoundSystem and a seat as parameters.
    public Musician(int instrumentID, int softVolume, int loudVolume, SoundSystem soundSystem, Integer seat) {
        // Set all corresponding instance variables
        this.instrumentID = instrumentID;
        this.loudVolume = loudVolume;
        this.softVolume = softVolume;
        // Default all musicans to play softly.
        this.setVolume = this.softVolume;
        this.soundSystem = soundSystem;
        this.seat = seat;
        // Inform the SoundSystem of the musician's ID and seat number.
        soundSystem.setInstrument(seat, instrumentID);
    }
    
    // readMusic() method. Takes and int[] as a parameter.
    public void readMusic(int[] arrayOfNotes) {
        // Store the notes in an instance variable.
        this.arrayOfNotes = arrayOfNotes;
    }
    
    // getNumberOfNotes method. Returns the amount of notes the musician has been given to play.
    public int getNumberOfNotes() {
        return arrayOfNotes.length;
    }
    
    // setSeat method. Relocates a musician to a new seat and set the SoundSystem accordingly.
    public void setSeat(int seat) {
        this.seat = seat;
        soundSystem.setInstrument(seat, instrumentID);
    }
    
    public void playNextNote() throws MusicianEndOfLifeException {
        if (nextNote <= MAX_NOTES) {
            if (arrayOfNotes[nextNote] != 0) {
                soundSystem.playNote(seat, arrayOfNotes[nextNote], setVolume);
                previousNote = nextNote;
            }
            ++nextNote;
        } else if (nextNote > MAX_NOTES) {
            soundSystem.stopNote(seat, arrayOfNotes[previousNote], setVolume);
            throw new MusicianEndOfLifeException(this);
        }
        
    }
    
    public int getType() {
        return instrumentID;
    }
    
    // Instruct the musicians to play softly by setting the setvolume variable to soft.
    public void playSoft() {
        setVolume = softVolume;
    }
    
    // Instruct the musician to play loudly by setting the setVoleume variable to loud.
    public void playLoud() {
        setVolume = loudVolume;
    }
    
    // returns the arrayOfNotes the musician was read.
    public int[] getNotes() {
        return arrayOfNotes;
    }
    
    // returns the nextnote the musician should play next.
    public int getNextNote() {
        return nextNote;
    }
    
    // return the seat the musician is currently seated in,
    public int getSeat() {
        return seat;
    }
    
    // return the current volume the musian has been instructed to play at.
    public String getVolume() {
        // If the setVolume is set to loud, return "loud".
        if (setVolume == loudVolume) {
            return "loud";
        } else {
            // Else return "soft".
            return "soft";
        }
    }
}