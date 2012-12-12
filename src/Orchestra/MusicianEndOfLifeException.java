package Orchestra;

// Public class MusicianEndOfLifeException extends Exception. Is used to pass the exhausted musician to the conductor so he can replace him with one
// with the same caracteristics.
public class MusicianEndOfLifeException extends Exception {
    // Declare instance variable musician which points to the musician that has thrown the exception.
    protected Musician musician;
    
    // MusicianEndOFLifeException constructor. Takes a musician as a paremater, saves it in the insatnce variable and calls the Excpetion constructor.
    MusicianEndOfLifeException(Musician musician){
        super();
        this.musician = musician;
    }
    
    // getMusician method. Returns the musician who threw this exception.
    public Musician getMusician() {
        return musician;
    }
}