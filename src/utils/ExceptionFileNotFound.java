package utils;

import java.io.FileNotFoundException;

/**
 *
 * @author siror
 */
public class ExceptionFileNotFound extends FileNotFoundException{
    public ExceptionFileNotFound(String message) {
        super(message);
    }
}
