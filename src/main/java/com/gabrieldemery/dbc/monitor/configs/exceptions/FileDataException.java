package com.gabrieldemery.dbc.monitor.configs.exceptions;

public class FileDataException extends Exception {

    public FileDataException(){
        super("Data line of file is invalid.");
    }

    public FileDataException(String message){
        super(message);
    }

}
