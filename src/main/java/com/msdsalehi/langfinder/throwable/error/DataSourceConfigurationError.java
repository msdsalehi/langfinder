package com.msdsalehi.langfinder.throwable.error;

/**
 *
 * @author masoud
 */
public class DataSourceConfigurationError extends Error {

    public DataSourceConfigurationError() {
    }

    public DataSourceConfigurationError(String message) {
        super(message);
    }

    public DataSourceConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceConfigurationError(Throwable cause) {
        super(cause);
    }

    public DataSourceConfigurationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
