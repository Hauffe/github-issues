package com.github.githubissues.exceptions;

public class RemoteItemNotFoundException extends RuntimeException{

    public RemoteItemNotFoundException() {
        super();
    }

    public RemoteItemNotFoundException(String message) {
        super(message);
    }
}
