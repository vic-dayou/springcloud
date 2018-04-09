package com.example.oauthjwt.controller;

import com.example.oauthjwt.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @GetMapping("/employee")
    public Optional<Employee> getEmployee(@RequestParam String email) {
        return employees.stream().filter(x -> x.getEmail().equals(email)).findAny();
    }


    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody Employee employee){
        employees.add(employee);
    }


    //test
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(){
        return "Hello,World!";
    }
}
