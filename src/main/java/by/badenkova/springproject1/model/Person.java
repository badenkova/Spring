package by.badenkova.springproject1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Person {

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private Date birthday;
    private String phone;

}

