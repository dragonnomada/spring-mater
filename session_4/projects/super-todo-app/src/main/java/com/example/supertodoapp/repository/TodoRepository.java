package com.example.supertodoapp.repository;

import com.example.supertodoapp.entity.Todo;
import com.example.supertodoapp.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    Iterable<Todo> findAllByUser(User user);

}
