package ch.m324.controller;

import ch.m324.model.Person;
import ch.m324.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personen")
@CrossOrigin(origins = "*")
public class PersonController {
    
    private final PersonRepository personRepository;
    
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersonen() {
        List<Person> personen = personRepository.findAll();
        return ResponseEntity.ok(personen);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        person.setId(id);
        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

