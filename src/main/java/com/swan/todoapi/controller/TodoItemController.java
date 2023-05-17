package com.swan.todoapi.controller;

import com.swan.todoapi.exception.ResourceNotFoundException;
import com.swan.todoapi.repository.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.swan.todoapi.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/todo")
public class TodoItemController {
    private final Logger log= LoggerFactory.getLogger(TodoItemController.class);

    private final TodoItemRepository todoItemRepository;


    @Autowired
    public TodoItemController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }


    @GetMapping("/")
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> todoItems = StreamSupport.stream(todoItemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return new ResponseEntity<>(todoItems, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem createdTodoItem = todoItemRepository.save(todoItem);
        return new ResponseEntity<>(createdTodoItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        TodoItem existingTodoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo item not found with id: " + id));

        existingTodoItem.setDescription(todoItem.getDescription());
        existingTodoItem.setCompleted(todoItem.isCompleted());
        existingTodoItem.setModifiedDate(Instant.now());

        TodoItem updatedTodoItem = todoItemRepository.save(existingTodoItem);
        return new ResponseEntity<>(updatedTodoItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        todoItemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
