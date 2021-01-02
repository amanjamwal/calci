package calci.exception;

import calci.CalciException;

public final class IllegalParametersException extends CalciException {
    public IllegalParametersException(String message) {
        super(message);
    }
}
