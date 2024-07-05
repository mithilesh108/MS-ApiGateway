package com.cts.apigateway.exception;

public class ResourceNotInsertedException extends RuntimeException{

    public ResourceNotInsertedException(String message){
        super(message);
    }

    public ResourceNotInsertedException(){
        super();
    }
}
