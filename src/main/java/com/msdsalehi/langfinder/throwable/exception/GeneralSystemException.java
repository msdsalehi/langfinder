package com.msdsalehi.langfinder.throwable.exception;

/**
 *
 * @author masoud
 */
public class GeneralSystemException extends RuntimeException {

    public GeneralSystemException() {
    }

    public GeneralSystemException(String message) {
        super(message);
    }

    public GeneralSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralSystemException(Throwable cause) {
        super(cause);
    }

    public GeneralSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
