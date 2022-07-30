package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.exception;

public class TodoCollectionException extends Exception {

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) { return "Todo with " + id + " NOT FOUND"; }

    public static String TodoAlreadyExists() {
        return "Todo with given name already exists";
    }

}
