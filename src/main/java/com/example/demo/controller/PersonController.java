package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/v1/{country}/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping //or requestmapping default maps to get request
    //or if your name is not the same eg map to "v1/{foo}/person" then @PathVariable just put @PathVariable("foo")
    public List<Person> getAllPersons(@NotBlank @PathVariable String country) {
        return personService.getAllPersons();
    }

    @RequestMapping(params = "name")
    public List<Person> getPersonInfo(@NotBlank @PathVariable String country,
                                      @RequestParam String name) {
        return personService.getPerson(name);
    }

    @PostMapping //@RequestMapping(method = RequestMethod.POST, value = "/v1/{country}/person")
    public void addPerson(@NotBlank @PathVariable String country,
                          @RequestBody Person person) {
        personService.addPerson(person);
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, params = "name")
    public void updatePerson(@NotBlank @PathVariable String country,
                             @RequestParam String name, @RequestBody Person person) {
        personService.updatePerson(name, person);
    }

    @RequestMapping(method = RequestMethod.DELETE, params = "name")
    public void deletePerson(@NotBlank @PathVariable String country,
                             @RequestParam String name) {
        personService.deletePerson(name);
    }
}
