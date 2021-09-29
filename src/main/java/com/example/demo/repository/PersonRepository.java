package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>
//extends ...Repository<entity type, type of primary key>
//or extends CrudRepository<Person,Long>
{
    //can create custom methods here

    /*
    can just declare methods and dont implement if you follow specific naming conventions
    spring data jpa framework will look at the method name and figure out whats the implementation needed
    eg findBy{Name of Property} format eg findByName etc
    spring data jpa will split out the part after findBy and then try to match that part with any field in the entity
    then run a filter and try to match whatever string you passed in with the field
     */
    public List<Person> findByName(String name); //or can be findByDescription(String foo) etc

    //try to follow the return value conventions
    //eg the repo has a deleteById method which returns void so should follow to make it easier to use
    public void deleteByName(String name);

    /*
    if you referring to a property that is a String you can just do findBy{Property}
    but if you want to filter and match by another class/object for example
    if you referring to a property that is not a String but is an object and you are looking at a field inside that object/class
    then need to specify that field name as well

    eg if inside Course entity you have a Topic entity called topic and the Topic entity has a String id field
    and you want to find a lost of courses by the id of the Topic inside the Course
    then you can do like public List<Course> findByTopicId(String topicId);
    (just add into method name)
     */
}

