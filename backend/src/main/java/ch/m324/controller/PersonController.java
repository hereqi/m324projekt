package ch.m324.controller;

import ch.m324.model.Person;
import ch.m324.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personen")
@CrossOrigin(origins = "*")
public class PersonController {
    
    private final PersonService personService;
    
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersonen() {
        List<Person> personen = personService.getAllPersonen();
        return ResponseEntity.ok(personen);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return personService.getPersonById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        Person saved = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person person) {
        Person updated = personService.updatePerson(id, person);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personService.deletePerson(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

