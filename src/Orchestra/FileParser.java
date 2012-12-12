package Orchestra;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

// Public class FileParser. Is used to parse the mus file into A classname (for reflection), a string (for the volume) and an array of ints (notes).
public class FileParser {
    // Declare instance variables to store the filename and declare a bufferedReader
    protected String filename;
    protected BufferedReader reader;
    // Declare a Map that will use a Properties object to read a file containing mappings of the musician's class and the syntax in the mus files.
    protected Map<Object, Object> musicianTypes = new Properties();
    
    // FileParser constructor. Attempt to create a new buffered reader from the mus file, else print an error message and quit.
    public FileParser(String filename){
        try {
            this.reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("[FATAL] File not Found");
            System.exit(1);
        }
    }
    
    public Object[] parseLine() {
        try {
            String[] readLine = reader.readLine().split(":");
            Object[] parsedLine = new Object[3];
            
            parsedLine[0] = (getMusicianClass(readLine[0]));
            parsedLine[1] = (readLine[1]);
            
            int notes[] = new int[readLine[2].split(",").length];
            for (int i=0; i < readLine[2].split(",").length; i++){
                notes[i] = Integer.parseInt(readLine[2].trim().split(",")[i]);
            }
            
            parsedLine[2] = notes;
            
            return parsedLine;
        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.err.println("File is not readable or is in an unsupported format");
            return new Object[0];
        }
    }
    
    // ready method. Checks if a new line is availble, if not returns false.
    public Boolean ready() {
        try {
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }
    
    // getMusicianClass method. Return the the short name of the class corresponding to the mus file musician type.
    public String getMusicianClass(String musician) {
        return (String) musicianTypes.get(musician);
    }
    
    // setMusicianClass method. Load the file conatining mappings of Class names and instrument names and add it 
    public void setMusicianClass(String filename) {
        try {
            ((Properties) musicianTypes).load(new FileReader(filename));
        } catch (IOException e) {
            System.err.println("Musician Types file not found");
        }
    }
}