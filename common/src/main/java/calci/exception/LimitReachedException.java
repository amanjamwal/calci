package calci.exception;

import calci.CalciException;

public final class LimitReachedException extends CalciException {
    public LimitReachedException(String message) {
        super(message);
    }
}
