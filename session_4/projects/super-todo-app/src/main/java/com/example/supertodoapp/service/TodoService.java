package com.example.supertodoapp.service;

import com.example.supertodoapp.entity.Todo;
import com.example.supertodoapp.entity.User;
import com.example.supertodoapp.repository.TodoRepository;
import com.example.supertodoapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public Iterable<Todo> allTodos(String username) throws Exception {
        User user = userRepository.searchUserByUsername(username)
                .orElseThrow(() -> new Exception("No se encuentra el usuario"));

        return todoRepository.findAllByUser(user);
    }

    private void verifyTodoTitle(String title) throws Exception {
        if (title.isBlank() || title.isEmpty()) {
            throw new Exception("El título no puede estar vacío");
        }
    }

    public Todo addTodo(String username, String title) throws Exception {
        verifyTodoTitle(title);

        User user = userRepository.searchUserByUsername(username)
                .orElseThrow(() -> new Exception("No se encuentra el usuario"));

        return todoRepository.save(Todo.builder()
                .title(title)
                .checked(false)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .user(user)
                .build());
    }

    public Todo updateTodoTitle(Long todoId, String title) throws Exception {
        verifyTodoTitle(title);

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new Exception("No se encontró el Todo"));

        todo.setTitle(title);

        todo.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return todoRepository.save(todo);
    }

    public Todo checkTodo(Long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new Exception("No se encontró el Todo"));

        todo.setChecked(true);

        todo.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return todoRepository.save(todo);
    }

    public Todo uncheckTodo(Long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new Exception("No se encontró el Todo"));

        todo.setChecked(false);

        todo.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return todoRepository.save(todo);
    }

    public Todo removeTodo(Long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new Exception("No se encontró el Todo"));

        todoRepository.delete(todo);

        return todo;
    }

}
