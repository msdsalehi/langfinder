package com.msdsalehi.langfinder.throwable.error;

/**
 *
 * @author masoud
 */
public class DataFormatError extends Error {

    public DataFormatError() {
    }

    public DataFormatError(String message) {
        super(message);
    }

    public DataFormatError(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatError(Throwable cause) {
        super(cause);
    }

    public DataFormatError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
