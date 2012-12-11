package Orchestra;

public abstract class Musician {
    protected int instrumentID;
    protected int softVolume;
    protected int loudVolume;
    protected SoundSystem soundSystem;
    protected int seat;
    protected int maxNotes = 100;
    protected int[] arrayOfNotes;
    protected int nextNote = 0;
    protected int setVolume;
        
    public Musician(int instrumentID, int softVolume, int loudVolume, SoundSystem soundSystem, Integer seat) {
        this.instrumentID = instrumentID;
        this.loudVolume = loudVolume;
        this.softVolume = softVolume;
        this.soundSystem = soundSystem;
        this.seat = seat;
        soundSystem.setInstrument(seat, instrumentID);
    }
    
    public void readMusic(int[] arrayOfNotes) {
        this.arrayOfNotes = arrayOfNotes;
    }
    
    public int getNumberOfNotes() {
        return arrayOfNotes.length;
    }
    
    public void setSeat(int seat) {
        this.seat = seat;
        soundSystem.setInstrument(seat, instrumentID);
    }
    
    public void playNextNote() {
        if (nextNote <= this.getNumberOfNotes() && arrayOfNotes[nextNote] != 0) {
            soundSystem.playNote(seat, arrayOfNotes[nextNote], setVolume);
        }
        ++nextNote;
    }
    
    public int getType() {
        return this.instrumentID;
    }
    
    public void playSoft() {
        this.setVolume = this.softVolume;
    }
    
    public void playLoud() {
        this.setVolume = this.loudVolume;
    }
    
    public int[] getNotes() {
        return arrayOfNotes;
    }
}