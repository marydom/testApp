package com.etnetera.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
@RequestMapping(value = "/frameworks", produces = { MediaType.APPLICATION_JSON_VALUE })
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

    @PostMapping
    JavaScriptFramework createOrSaveFramework(@RequestBody JavaScriptFramework newFramework) {
        return repository.save(newFramework);
    }
 
    @GetMapping("/{id}")
    JavaScriptFramework getFrameworkById(@PathVariable Long id) {
        return repository.findById(id).get();
    }
 
    @PutMapping("/{id}")
    JavaScriptFramework updateFramework(@RequestBody JavaScriptFramework newFramework, @PathVariable Long id) {
 
        return repository.findById(id).map(framework -> {
            framework.setName(newFramework.getName());
            framework.setDeprecationDate(newFramework.getDeprecationDate());
            framework.setHypeLevel(newFramework.getHypeLevel());
            framework.setVersion(newFramework.getVersion());
            return repository.save(framework);
        }).orElseGet(() -> {
            newFramework.setId(id);
            return repository.save(newFramework);
        });
    }
 
    @DeleteMapping("/{id}")
    void deleteFramework(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
