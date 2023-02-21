package com.example.supertodoapp.controller;

import com.example.supertodoapp.entity.Todo;
import com.example.supertodoapp.service.SecurityService;
import com.example.supertodoapp.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@CrossOrigin
public class TodoController {

    private final SecurityService securityService;
    private final TodoService todoService;

    public TodoController(SecurityService securityService, TodoService todoService) {
        this.securityService = securityService;
        this.todoService = todoService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Todo>> getAllTodos(@RequestParam String token) {

        try {
            String username = securityService.verifyPermission(token, "todo:all");
            return ResponseEntity.ok(todoService.allTodos(username));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

    @PutMapping("/add")
    public ResponseEntity<Todo> addTodo(@RequestParam String token, @RequestBody Todo todo) {

        try {
            String username = securityService.verifyPermission(token, "todo:add");
            return ResponseEntity.ok(todoService.addTodo(username, todo.getTitle()));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

    @PostMapping("/{todoId}/check")
    public ResponseEntity<Todo> checkTodo(@RequestParam String token, @PathVariable long todoId) {

        try {
            String username = securityService.verifyPermission(token, "todo:edit");
            return ResponseEntity.ok(todoService.checkTodo(todoId));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

    @PostMapping("/{todoId}/uncheck")
    public ResponseEntity<Todo> unckeckTodo(@RequestParam String token, @PathVariable long todoId) {

        try {
            String username = securityService.verifyPermission(token, "todo:edit");
            return ResponseEntity.ok(todoService.checkTodo(todoId));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

    @PostMapping("/{todoId}/update/title")
    public ResponseEntity<Todo> updateTodoTitle(@RequestParam String token,
                                                @PathVariable long todoId,
                                                @RequestBody String title) {

        try {
            String username = securityService.verifyPermission(token, "todo:edit");
            return ResponseEntity.ok(todoService.updateTodoTitle(todoId, title));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

    @DeleteMapping("/{todoId}/remove")
    public ResponseEntity<Todo> updateTodoTitle(@RequestParam String token,
                                                @PathVariable long todoId) {

        try {
            String username = securityService.verifyPermission(token, "todo:delete");
            return ResponseEntity.ok(todoService.removeTodo(todoId));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

    }

}
