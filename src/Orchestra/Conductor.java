package Orchestra;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Conductor {
    protected SoundSystem soundSystem;
    protected Orchestra orchestra;
    
    public Conductor(String filename) {
        soundSystem  = new SoundSystem();
        soundSystem.init();
        orchestra = new Orchestra();
                
        FileParser file = new FileParser(filename);
        
        int longestNumberOfNotes = 0;
        
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
            
            orchestra.seatMusician(musician, seat);
            
        }
                    
        for(int i=0; i<longestNumberOfNotes; i++){
            for(Musician musician : orchestra.getArrayOfMusicians()) {
                musician.playNextNote();
            }
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
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
    
    public static void main(String[] args){
       Conductor conductor = new Conductor(args[0]);
    }
}