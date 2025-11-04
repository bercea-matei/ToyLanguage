package toy_language.domain.my_exceptions;

/*
 * this class exists only to bundle all the exceptions under one group so some methods that could throw all exceptions (like PrgState execute) would not be so clutered
 */
public class ToyLanguageExceptions extends Exception{
    public ToyLanguageExceptions(String msg) {
        super(msg);
    }
}
