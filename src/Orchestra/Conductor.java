package Orchestra;

import java.lang.reflect.InvocationTargetException;

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
            Musician musician = createMusician((String)fileLine[0], seat);
            
            if(((String)fileLine[1]).equalsIgnoreCase("loud")){
                musician.playLoud();
            } else {
                musician.playSoft();
            }
            
            musician.readMusic((int[])fileLine[2]);
            
            if(musician.getNumberOfNotes() > longestNumberOfNotes) {
                longestNumberOfNotes = musician.getNumberOfNotes();
            }
            try {
                orchestra.addMusician(musician, seat);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public Musician createMusician(String musicianType, Integer seat) {
        Musician musician;
        
        try {
            musician = (Musician) Class.forName("Orchestra." + musicianType).getConstructor(SoundSystem.class,Integer.class).newInstance(soundSystem, seat);
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
            System.err.println("Unsupported Musician Type!");
            musician = null;
        }
        return musician;
    }
    
    public void play() {
        for(int i=0; i<longestNumberOfNotes; i++){
            for(Musician musician : orchestra.getArrayOfMusicians()) {
                musician.playNextNote();
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