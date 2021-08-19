package com.etnetera.hr.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	public Iterable<JavaScriptFramework> getFrameworks() {
		return repository.findAll();
	}

    @PostMapping
    public JavaScriptFramework createOrSaveFramework(@Valid @RequestBody JavaScriptFramework newFramework) {
        return repository.save(newFramework);
    }
 
    @GetMapping("/{id}")
    public JavaScriptFramework getFrameworkById(@PathVariable("id") Long id) {
        return repository.findById(id).orElseThrow();
    }
 
    @GetMapping("/with-name/{name}")
    public List <JavaScriptFramework> getFrameworksByName(@PathVariable("name") String name) {
        return repository.findByName(name);
    }
 
    @GetMapping("/actuals-tomorrow")
    public List<JavaScriptFramework> getFrameworksByDeprecationDateToday() {
        return repository.findByDeprecationDateAfter(LocalDate.now());
    }
 
    @GetMapping("/actuals-after/{date}")
    public List<JavaScriptFramework> getFrameworksByDeprecationDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date) {
        return repository.findByDeprecationDateAfter(date);
    }
 
    @GetMapping("/with-hype/{hypeLevel}")
    public List<JavaScriptFramework> getFrameworksByHypeLevel(@PathVariable("hypeLevel") Integer hypeLevel) {
        return repository.findByHypeLevelGreaterThanOrderByHypeLevelDesc(hypeLevel);
    }
 
    @PutMapping("/{id}")
    public JavaScriptFramework updateFramework(@Valid @RequestBody JavaScriptFramework newFramework, @PathVariable("id") Long id) {
 
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
 
    @DeleteMapping
    public void deleteFrameworks() {
        repository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteFramework(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
