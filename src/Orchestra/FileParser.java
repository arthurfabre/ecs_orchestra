package Orchestra;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileParser {
    protected String filename;
    protected BufferedReader reader;
    protected HashMap<String, String> musicanTypes = new HashMap();
    
    public FileParser(String filename){
        try {
            this.reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File not Found.");
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
    
    public Boolean ready() {
        try {
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }
    
    public String getMusicianClass(String musician) {
        musicanTypes.put("leadviolin", "LeadViolin");
        musicanTypes.put("cello", "Cello");
        musicanTypes.put("violin", "Violin");
        musicanTypes.put("piano", "Piano");
        musicanTypes.put("trumpet", "Trumpet");
        
        return musicanTypes.get(musician);
    }
}