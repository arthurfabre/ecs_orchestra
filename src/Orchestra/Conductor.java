package Orchestra;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

//public Class conductor. Takes care of creating musicians, seating them in the orchestra ans instructing them to play their notes (be it either directly or
// through a LeadViolinist.
public class Conductor {
    // Declare insatnce variables that the condcutor will be using to communicate with the orchestra and the SoundSystem.
    protected SoundSystem soundSystem;
    protected Orchestra orchestra;
    // int to store the musician with the longest number of notes to know when the song is over.
    protected int longestNumberOfNotes = 0;
    // RYTHM and CLASS_FILE constans. used to specify the duration between 2 notes in ms and the file that contains the mappins from ClassNames to mus names.
    protected final int RYTHM = 200;
    protected final String CLASS_FILE = "class.txt";
    
    // Cnducotor constructor. Take a filename as a parameter (the mus file to be played).
    public Conductor(String filename) {
        // Create the SoundSystem and initialize it. 
        soundSystem  = new SoundSystem();
        soundSystem.init();
        // Create a new Orchestra to store the musicians.
        orchestra = new Orchestra();
        
        // Create a Fileparser to parse the mus file, and set the Class Name to mus name mappings.
        FileParser file = new FileParser(filename);
        file.setMusicianClass(CLASS_FILE);
        
        // While anbother line is ready to be read,
        while (file.ready()) {
            // get the next available seat in the orchestra.
            int seat = orchestra.getNextAvailableSeat();
            System.out.println(seat);
            
            // Get the object array containing the class name, volume and notes array for that line.
            Object[] fileLine = file.parseLine();
            // Call the createMusician method with the classname (fileLine[0]), the designated seat, the volume (fileLine[1]) and the array of notes
            // (fileLine[2])
            createMusician((String)fileLine[0], seat, (String)fileLine[1], (int[])fileLine[2]);
            System.out.println("musician created!");
        }
    }
    
    // createMusician method. Takes a musicianType, a seat, a specified volume and an array of notes as parameters.
    // Creates a musician of that type, set the volume, read him his notes and add him to the orchestra.
    public void createMusician(String musicianType, Integer seat, String volume, int[] notes) {
        // Declare a generic musician.
        Musician musician;
        
        // Initialize the musician by finding the apropriate class (Fully qulifed name, ie packageName.className)
        // get the constructor of that class that amtches the specified signature (SoundSystem.class,Integer.class)
        // make a new insatnce with the paramaters soundSystem and seat.
        try {
            musician = (Musician) Class.forName("Orchestra." + musicianType).getConstructor(SoundSystem.class,Integer.class).newInstance(soundSystem, seat);
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
            // If a musician of specified type cannot be created, print an error message and quit.
            System.err.println("[FATAL] Failed to create musician");
            musician = null;
            System.exit(1);
        }
        
        // Set the volume that the musician zill play at. 
        if(volume.equalsIgnoreCase("loud")){
                musician.playLoud();
            } else {
                musician.playSoft();
            }
        
        // Read the array of notes Notes[] to the musician using nthe readMusic method. 
        musician.readMusic(notes);
        
        // If the musician has more notes than the current recorded maximum , redefine the current recorded maximum.
        // NOTE: For pragmatic reasons, it was chosen to count (0)s as notes.
        if(musician.getNumberOfNotes() > longestNumberOfNotes) {
            longestNumberOfNotes = musician.getNumberOfNotes();
        }
        
        // add the musician to the orchestra. Check if a musician is returned by the addMusician method, if it is the case the musician is relocated to
        // the next free seat if there is one.
        try {
            Musician previousMusician = orchestra.addMusician(musician, seat);
            if (previousMusician != null) {
                int newSeat = orchestra.getNextAvailableSeat();
                orchestra.addMusician(previousMusician, newSeat);
                previousMusician.setSeat(newSeat);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void musicianReplacer(MusicianEndOfLifeException e) {
        createMusician(e.getMusician().getClass().getSimpleName(), e.getMusician().getSeat(), e.getMusician().getVolume(), Arrays.copyOfRange(e.getMusician().getNotes(), e.getMusician().getNextNote(), e.getMusician().getNotes().length - 1));
    }
    
    public void play() {
        System.out.println("notes: " + longestNumberOfNotes);
        for(int i=0; i<longestNumberOfNotes; i++){
            if (orchestra.getArrayOfMusicians().containsGeneric("LeadViolinist")) {
                try {
                    LeadViolinist leadViolinist = (LeadViolinist)orchestra.getArrayOfMusicians().getGeneric("LeadViolinist");
                    leadViolinist.init(orchestra);
                    leadViolinist.playNextNote();
                    System.out.println("LeadViolonist");
                } catch (MusicianEndOfLifeException e) {
                    musicianReplacer(e);
                }
            } else {
                for(Musician musician : orchestra.getArrayOfMusicians()) {
                    System.out.println("id: " + musician.getType());
                    try {
                        musician.playNextNote();
                    } catch (MusicianEndOfLifeException e) {
                        musicianReplacer(e);
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