package calci.exception;

import calci.CalciException;

public final class InsufficientParametersException extends CalciException {
    public InsufficientParametersException(String message) {
        super(message);
    }
}
