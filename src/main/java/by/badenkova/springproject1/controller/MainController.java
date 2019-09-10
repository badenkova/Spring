package by.badenkova.springproject1.controller;

import by.badenkova.springproject1.form.PersonForm;
import by.badenkova.springproject1.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class MainController {
    private static List<Person> persons = new ArrayList<Person>();
    static {
        persons.add(new Person("Olga", "Pertova", "Gorky str", "Minsk", "287324","olga@gmail.com",new java.util.Date(1234566878), "+375291112324"));
        persons.add(new Person("Nikolai", "Ivanov",  "Gorky str", "Minsk", "287324","nikolai@gmail.com",new java.util.Date(1234563878),"+375291112324"));
    }
//    private static final org.slf4j.Logger log =
//            org.slf4j.LoggerFactory.getLogger(MainController.class);
// Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personList");
        model.addAttribute("persons", persons);
        return modelAndView;
    }
    @GetMapping(value = {"/addPerson"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addPerson");
        var personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
        return modelAndView;
    }
    // @PostMapping("/addPerson")
//GetMapping("/")
    @PostMapping(value = {"/addPerson"})
    public ModelAndView savePerson(Model model,
    @Valid @ModelAttribute("personForm") PersonForm personForm, Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.setViewName("addPerson");
        }
        else {
        modelAndView.setViewName("personList");
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();String street = personForm.getStreet();
            String city = personForm.getCity();
            String zip = personForm.getZip();
            String email = personForm.getEmail();
            Date birthday = (Date)personForm.getBirthday();
            String phone = personForm.getPhone();
            Person newPerson = new Person(firstName, lastName, street, city,zip, email, birthday, phone);
            persons.add(newPerson);
            model.addAttribute("persons", persons);
            log.info("/addPerson - POST was called");
            return modelAndView;
        }
        return modelAndView;
    } }