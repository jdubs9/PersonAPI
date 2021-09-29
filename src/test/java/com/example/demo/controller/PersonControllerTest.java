package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should get all persons when making GET requests to endpoint /v1/sg/person with no query params")
    public void shouldGetAllPersons() throws Exception {
        Person person1 = new Person("Nicole Tan", 20, "S546080");
        Person person2 = new Person("Marcus Pang", 36, "S569933");

        when(personService.getAllPersons()).thenReturn(Arrays.asList(person1, person2));

        mockMvc.perform(get("/v1/sg/person"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Nicole Tan")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].address", is("S546080")))
                .andExpect(jsonPath("$[1].name", is("Marcus Pang")))
                .andExpect(jsonPath("$[1].age", is(36)))
                .andExpect(jsonPath("$[1].address", is("S569933")));
    }

    @Test
    @DisplayName("Should get all persons of given name when making GET requests to endpoint /v1/sg/person with name query param")
    public void shouldGetPersonOfSameName() throws Exception {
        Person person1 = new Person("Nicole", 20, "S546080");
        Person person2 = new Person("Nicole", 35, "S569933");

        when(personService.getPerson("Nicole")).thenReturn(Arrays.asList(person1, person2));

        mockMvc.perform(get("/v1/sg/person?name=Nicole"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Nicole")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].address", is("S546080")))
                .andExpect(jsonPath("$[1].name", is("Nicole")))
                .andExpect(jsonPath("$[1].age", is(35)))
                .andExpect(jsonPath("$[1].address", is("S569933")));
    }

    @Test
    @DisplayName("Should return empty list when no person of the given name is found when making GET requests to endpoint /v1/sg/person with name query param")
    public void whenNameNotFound_shouldReturnEmptyList() throws Exception {

        when(personService.getPerson("Rachel")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/sg/person?name=Rachel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(0)))
                .andExpect(content().string("[]"));
    }

    @Test
    @DisplayName("Should return empty when making POST requests to endpoint /v1/sg/person with no query params and with person body")
    public void shouldAddPersonOnPost() throws Exception {
        Person person1 = new Person("Nicole Chew", 25, "S546080");

        when(personService.addPerson(person1)).thenReturn(person1);

        mockMvc.perform(post("/v1/sg/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"1\", \"name\": \"Rachel Lee\", \"age\": 26, \"address\": \"S546080\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("Should return empty when making POST requests to endpoint /v1/sg/person with name query params and with person body")
    public void shouldUpdatePersonOnPost() throws Exception {
        Person person1 = new Person("Nicole", 25, "S546080");

        when(personService.updatePerson("Nicole", person1)).thenReturn(person1);

        mockMvc.perform(post("/v1/sg/person?name=Nicole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"name\": \"Nicole Lee\", \"age\": 27, \"address\": \"S546080\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("Should return empty when making DELETE requests to endpoint /v1/sg/person with name query param")
    public void shouldDeletePersonOnPost() throws Exception {

        doNothing().when(personService).deletePerson("Nicole");

        mockMvc.perform(delete("/v1/sg/person?name=Nicole")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
