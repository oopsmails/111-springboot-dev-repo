package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.service;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.exception.TodoCollectionException;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.TodoDTO;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException {
        var todoOptional = todoRepository.findByTodo(todoDTO.getTodo());
        if (todoOptional.isPresent())
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());

        todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
        todoRepository.save(todoDTO);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        var todos = todoRepository.findAll();
        if (todos.size() > 0)
            return todos;
        return new ArrayList<TodoDTO>();
    }

    @Override
    public TodoDTO get(String id) throws TodoCollectionException {
        var todoDto = todoRepository.findById(id);
        if (!todoDto.isPresent())
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        return todoDto.get();
    }

    @Override
    public void update(String id, TodoDTO todo) throws TodoCollectionException {
        var todoWithId = todoRepository.findById(id);
        var todoWithSameName = todoRepository.findByTodo(todo.getTodo());

        if (!todoWithId.isPresent())
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));

        if(todoWithSameName.isPresent() && todoWithSameName.get().getId().equals(id))
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());

        TodoDTO todoToUpdate = todoWithId.get();

        todoToUpdate.setTodo(todo.getTodo());
        todoToUpdate.setDescription(todo.getDescription());
        todoToUpdate.setCompleted(todo.getCompleted());
        todo.setUpdatedAt(new Date(System.currentTimeMillis()));
        todoRepository.save(todoToUpdate);
    }

    @Override
    public void delete(String id) throws TodoCollectionException {
        var todo = todoRepository.findById(id);
        if(!todo.isPresent())
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        todoRepository.deleteById(id);
    }
}
