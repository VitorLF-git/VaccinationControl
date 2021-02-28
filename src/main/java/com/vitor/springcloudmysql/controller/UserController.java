package com.vitor.springcloudmysql.controller;

import com.vitor.springcloudmysql.model.User;
import com.vitor.springcloudmysql.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping({"/users"})
public class UserController {

    private UserRepository repository;
    UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<User> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody User user){
    	try {    		
            repository.save(user);
             return new ResponseEntity<>(
     	            user, 
     	            HttpStatus.CREATED);
    	} catch (DataIntegrityViolationException e) {
    		HashMap<String,Object> response = new HashMap<String,Object>();
            response.put("messageError", "User email or CPF already exists");
    	    System.out.println("User email or CPF already exists");
    	    return ResponseEntity.badRequest().body(response);
    	}
    }
    
    @PutMapping(value="/{id}")
    public ResponseEntity<User> update(@PathVariable("id") long id,
                                          @Valid @RequestBody User user){
      return repository.findById(id)
          .map(record -> {
              record.setName(user.getName());
              record.setEmail(user.getEmail());
              record.setCpf(user.getCpf());
              record.setBirthday(user.getBirthday());

              User updated = repository.save(record);
              return ResponseEntity.ok().body(updated);
          }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
      return repository.findById(id)
          .map(record -> {
              repository.deleteById(id);
              return ResponseEntity.ok().build();
          }).orElse(ResponseEntity.notFound().build());
    }
}