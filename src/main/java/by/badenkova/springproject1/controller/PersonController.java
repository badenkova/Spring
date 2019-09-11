package by.badenkova.springproject1.controller;


import by.badenkova.springproject1.dto.NewPersonDto;
import by.badenkova.springproject1.entity.Person;
import by.badenkova.springproject1.exceptions.NoSuchEntityException;
import by.badenkova.springproject1.service.PersonService;
//import lombok.Value;
import org.springframework.beans.factory.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class PersonController {
    private final PersonService personService;
    // private static final org.slf4j.Logger log =
// org.slf4j.LoggerFactory.getLogger(MainController.class);
// Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("index was called");
        return modelAndView;
    }
    @GetMapping(value = {"/personList"})
    public ModelAndView personList(Model model) {
        List<Person> persons = (List<Person>) personService.getAllPerson();
        log.info("person List" + persons);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personList");
        model.addAttribute("persons", persons);
        log.info("/personList was called");
        return modelAndView;
    }
    @GetMapping(value = {"/addPerson"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addPerson");
        NewPersonDto personForm = new NewPersonDto();
        model.addAttribute("personForm", personForm);
        log.info("/addPerson - GET was called" + personForm);
        return modelAndView;
    }
    // @PostMapping("/addPerson")
//GetMapping("/")
    @PostMapping(value = {"/addPerson"})
    public ModelAndView savePerson(Model model, //
                                   @Valid @ModelAttribute("personForm")
                                           NewPersonDto personDto,
                                   Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        log.info("/addPerson - POST was called" + personDto);
        if (errors.hasErrors()) {
            modelAndView.setViewName("addPerson");
        }
        else {
            modelAndView.setViewName("personList");
            Long id = personDto.getPersonId();
            String firstName = personDto.getFirstName();
            String lastName = personDto.getLastName();
            String street = personDto.getStreet();
            String city = personDto.getCity();
            String zip = personDto.getZip();
            String email = personDto.getEmail();
            Date birthday = (Date)personDto.getBirthday();
            String phone = personDto.getPhone();
            Person newPerson = new Person(id, firstName, lastName, street,city, zip, email, birthday, phone);
            personService.addNewPerson(newPerson);
            model.addAttribute("persons", personService.getAllPerson());
            log.info("/addPerson - POST was called");
            return modelAndView;
        }
        return modelAndView;
    }
    @RequestMapping(value = "/editPerson/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id) throws NoSuchEntityException {
        Person person = personService.getById(id).orElseThrow(()-> new
                NoSuchEntityException("Person not found") );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPerson");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public ModelAndView editPerson( @Valid @ModelAttribute("person") Person person,  Errors errors) {
        log.info("/editPerson - POST was called"+ person);
        personService.addNewPerson(person);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/personList");
        return modelAndView;
    }
    @RequestMapping(value = "/deletePerson/{id}", method = RequestMethod.GET)
    public ModelAndView deletePerson(@PathVariable("id") Long id) throws
            NoSuchEntityException {
        Person person = personService.getById(id).orElseThrow(()-> new
                NoSuchEntityException("Person not found") );
        personService.deletePerson(person);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/personList");
        return modelAndView;
    }
}

