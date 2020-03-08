package com.pulak.filedownloader.exception;

/**
 * Unsupported protocol exception.
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class UnsupportedProtocolException extends Exception {
    public UnsupportedProtocolException() {
        super();
    }

    public UnsupportedProtocolException(final String msg) {
        super(msg);
    }
}