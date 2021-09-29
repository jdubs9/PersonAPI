package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//business services are basically singletons
//when the application starts up spring creates an instance of this service and keeps it in memory
@Service
public class PersonService {
    /*//Arrays.asList lists are immutable
    private List<Person> persons = new ArrayList<>(Arrays.asList(
            new Person("Nicole Lee", 24, "S546080"),
            new Person("Marcus Tan", 31, "S569933"),
            new Person("Jun Jie", 24, "S310145"),
            new Person("Wei Ming", 24, "S520147"),
            new Person("Claire Phua", 24, "S570150")
    ));*/

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    private void initPersons() {
        Person person1 = new Person("Nicole Lee", 24, "S546080");
        Person person2 = new Person("Marcus Tan", 31, "S569933");
        Person person3 = new Person("Jun Jie", 24, "S310145");
        Person person4 = new Person("Wei Ming", 24, "S520147");
        Person person5 = new Person("Claire Phua", 24, "S570150");
        personRepository.saveAll(List.of(person1, person2, person3, person4, person5));
    }

    public List<Person> getAllPersons() {
        //return persons;
        return personRepository.findAll();
    }

    public List<Person> getPerson(String name) {
        //return persons.stream().filter(t -> t.getName().equals(name)).findFirst().get();
        return personRepository.findByName(name);
    }

    public Person addPerson(Person person) {
        //persons.add(person); return person;
        return personRepository.save(person);
    }

    public Person updatePerson(String name, Person person) {
        /*
        for (int i = 0; i< persons.size(); i++) {
            Person t = persons.get(i);
            if (t.getName().equals(name)) {
                persons.set(i, person); //updates element of specified index with given element E
                return person;
            }
        }
         */
        return personRepository.save(person);
    }

    @Transactional
    public void deletePerson(String name) {
        //persons.removeIf(t -> t.getName().equals(name)); //removes from list if predicate lambda expression is true
        personRepository.deleteByName(name);
    }
}
