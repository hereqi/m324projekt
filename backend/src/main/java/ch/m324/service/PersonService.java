package ch.m324.service;

import ch.m324.model.Person;
import ch.m324.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    
    private final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public List<Person> getAllPersonen() {
        return personRepository.findAll();
    }
    
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }
    
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
    
    public Person updatePerson(Long id, Person person) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            return personRepository.save(person);
        }
        return null;
    }
    
    public boolean deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

