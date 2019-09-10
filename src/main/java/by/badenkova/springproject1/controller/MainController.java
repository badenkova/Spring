package by.badenkova.springproject1.controller;

import by.badenkova.springproject1.form.PersonForm;
import by.badenkova.springproject1.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private static List<Person> persons = new ArrayList<Person>();
    static {
        persons.add(new Person("Olga", "Pertova"));
        persons.add(new Person("Nikolai", "Ivanov"));
    }
    //
// Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }
    @RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personList");
        model.addAttribute("persons", persons);
        return modelAndView;
    }
    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addPerson");
        var personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
        return modelAndView;
    }
    // @PostMapping("/addPerson")
//GetMapping("/")
    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, //
    @ModelAttribute("personForm") PersonForm personForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personList");
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        if (firstName != null && firstName.length() > 0 && lastName != null && lastName.length() > 0) {
        Person newPerson = new Person(firstName, lastName);
        persons.add(newPerson);
        model.addAttribute("persons",persons);
        return modelAndView;
    }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addPerson");
        return modelAndView;
}
}