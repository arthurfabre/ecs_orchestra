package Orchestra;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Conductor {
    protected SoundSystem soundSystem;
    protected Orchestra orchestra;
    protected int longestNumberOfNotes = 0;
    protected final int RYTHM = 200;
    
    public Conductor(String filename) {
        soundSystem  = new SoundSystem();
        soundSystem.init();
        orchestra = new Orchestra();
        
        FileParser file = new FileParser(filename);
        file.setMusicianClass("class.txt");
        
        while (file.ready()) {
            int seat = orchestra.getNextAvailableSeat();
            
            Object[] fileLine = file.parseLine();
            createMusician((String)fileLine[0], seat, (String)fileLine[1], (int[])fileLine[2]);
        }
    }
    
    public void createMusician(String musicianType, Integer seat, String volume, int[] notes) {
        Musician musician;
        
        try {
            musician = (Musician) Class.forName("Orchestra." + musicianType).getConstructor(SoundSystem.class,Integer.class).newInstance(soundSystem, seat);
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
            System.err.println("Unsupported Musician Type!");
            musician = null;
        }
        
        if(volume.equalsIgnoreCase("loud")){
                musician.playLoud();
            } else {
                musician.playSoft();
            }
            
        musician.readMusic(notes);
            
        if(musician.getNumberOfNotes() > longestNumberOfNotes) {
            longestNumberOfNotes = musician.getNumberOfNotes();
        }
        try {
            orchestra.addMusician(musician, seat);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void exceptionHandler(MusicianEndOfLifeException e) {
        createMusician(e.getMusician().getClass().getSimpleName(), e.getMusician().getSeat(), e.getMusician().getVolume(), Arrays.copyOfRange(e.getMusician().getNotes(), e.getMusician().getNextNote(), e.getMusician().getNotes().length - 1));
    }
    
    public void play() {
        for(int i=0; i<longestNumberOfNotes; i++){
            if (orchestra.getArrayOfMusicians().containsGeneric("LeadViolinist")) {
                try {
                    LeadViolinist leadViolinist = (LeadViolinist)orchestra.getArrayOfMusicians().getGeneric("LeadViolinist");
                    leadViolinist.init(orchestra, this, RYTHM, longestNumberOfNotes);
                    leadViolinist.playNextNote();
                } catch (MusicianEndOfLifeException e) {
                    exceptionHandler(e);
                }
            } else {
                for(Musician musician : orchestra.getArrayOfMusicians()) {
                    try {
                        musician.playNextNote();
                    } catch (MusicianEndOfLifeException e) {
                        exceptionHandler(e);
                    }    
                }
            }
            
            try {
                Thread.sleep(RYTHM);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public static void main(String[] args){
       Conductor conductor = new Conductor(args[0]);
       
       conductor.play();
    }
}