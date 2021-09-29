package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    //MockMvc is only for weblayer ie controller and is not used in service test

    @InjectMocks PersonService personService;

    @Captor
    ArgumentCaptor<Person> personArgumentCaptor;

    @Test
    @DisplayName("Should get all persons from database")
    public void shouldGetAllPersons() throws Exception {
        Person person1 = new Person("Nicole", 20, "S546080");
        Person person2 = new Person("Rachel", 35, "S569933");
        List<Person> expected = Arrays.asList(person1, person2);
        when(personRepository.findAll()).thenReturn(expected);

        List<Person> result = personService.getAllPersons();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get all persons of this given name from database")
    public void shouldGetPersonsWithGivenName() throws Exception {
        Person person1 = new Person("Nicole", 21, "S546080");
        Person person2 = new Person("Nicole", 30, "S569933");
        List<Person> expected = Arrays.asList(person1, person2);
        when(personRepository.findByName("Nicole")).thenReturn(expected);

        List<Person> result = personService.getPerson("Nicole");

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should return empty list when no person of given name is found")
    public void whenNameNotFound_shouldReturnEmptyList() throws Exception {

        List<Person> expected = Collections.emptyList();
        when(personRepository.findByName("JingWen")).thenReturn(expected);

        List<Person> result = personService.getPerson("JingWen");

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should add a given person and return empty")
    public void shouldAddPerson() throws Exception {
        Person person1 = new Person("Nicole", 29, "S546080");
        when(personRepository.save(person1)).thenReturn(person1);

        personService.addPerson(person1);
        //check the number of method invocations (expected number of invocations passed in times() method)
        //capture the argument passed to save method
        verify(personRepository, times(1)).save(personArgumentCaptor.capture());
        Person personCaptorValue = personArgumentCaptor.getValue();
        assertEquals(personCaptorValue.getName(), person1.getName());
        assertEquals(personCaptorValue.getAge(), person1.getAge());
        assertEquals(personCaptorValue.getAddress(), person1.getAddress());
    }

    @Test
    @DisplayName("Should update person of given name and return empty")
    public void shouldUpdatePerson() throws Exception {
        Person person1 = new Person("Nicole Lim", 45, "S546080");
        when(personRepository.save(person1)).thenReturn(person1);

        personService.updatePerson("Nicole Lim", person1);
        verify(personRepository, times(1)).save(personArgumentCaptor.capture());
        Person personCaptorValue = personArgumentCaptor.getValue();
        assertEquals(personCaptorValue.getName(), person1.getName());
        assertEquals(personCaptorValue.getAge(), person1.getAge());
        assertEquals(personCaptorValue.getAddress(), person1.getAddress());
    }

    @Test
    @DisplayName("Should delete person of given name and return empty")
    public void shouldDeletePerson() throws Exception {

        doNothing().when(personRepository).deleteByName("Nicole");

        personService.deletePerson("Nicole");
        verify(personRepository, times(1)).deleteByName("Nicole");
    }
}
