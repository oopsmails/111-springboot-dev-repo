package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.service;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.exception.TodoCollectionException;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {

    void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException;

    List<TodoDTO> getAllTodos();

    TodoDTO get(String id) throws TodoCollectionException;

    void update(String id, TodoDTO todo) throws TodoCollectionException;

    void delete(String id) throws TodoCollectionException;

}
