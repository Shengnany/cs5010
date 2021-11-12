/**
 * InvalidArgumentException is thrown when the command line argument is illegal
 */
public class InvalidArgumentException extends Exception{

    /**
     * The constructor of the InvalidArgumentException
     * @param message the message of the Exception
     */
    public InvalidArgumentException(String message){
        super(message);
    }
}
