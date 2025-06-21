package com.luv2code.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController
{

    private  EmployeeService employeeService;  // Renamed for clarity
    private ObjectMapper objectMapper;

    @Autowired  // Explicit autowiring (optional in newer Spring)
    public EmployeeRestController(EmployeeService employeeService,ObjectMapper objectMapper) {
        this.employeeService=employeeService;
        this.objectMapper=objectMapper;
    }

//    GET LIST OF ALL EMPLOYEES
    @GetMapping("/employees")
    public List<Employee> findAllEmployees() {  // Renamed method
        return employeeService.findAll();
    }

//  GET DATA BY ID
    @GetMapping("/employees/{employeeId}")
    public Employee findByID(@PathVariable int employeeId)
    {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee==null)
            throw new RuntimeException("Object not found");

        return theEmployee;
    }

//  INSERT DATA
//  Post Mapping means that we will use HTTP METHOD POST instead of get
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee)
    {
//  we set the id to 0 because even if that employee exists in the database then also we will add that employee
//  with a different id
        theEmployee.setId(0);
        return employeeService.SaveEmployee(theEmployee);
    }

//   UPDATE ENTRY
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee)
    {
        Employee emp = employeeService.SaveEmployee(theEmployee);

        if (emp==null)
            throw new RuntimeException("Object not found");

        return emp;
    }

// HTTP METHOD PATCH FOR PARTIAL UPDATES
    @PatchMapping("/employees/{employeeId}")
    public Employee partiallyUpdateEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayLoad)
//    The above map contains json objects as map of key value pairs in patchPayLoad
    {
        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee ==null)
            throw new RuntimeException("Employee not found");

        if (patchPayLoad.containsKey("id"))
            throw new RuntimeException("Employee id not allowed to change ");

        Employee patchedEmployee = apply(patchPayLoad,tempEmployee);

        Employee dbEmployee = employeeService.SaveEmployee(patchedEmployee);

        return dbEmployee;
    }

//  APPLY IS USED IN @PatchMapping
    private Employee apply(Map<String, Object> patchPayLoad, Employee tempEmployee) {

//   CONVERT EMPLOYEE OBJECT TO JSON OBJECT NODES
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

//    CONVERT patchPayLoad map into a json object
        ObjectNode patchNode = objectMapper.convertValue(patchPayLoad, ObjectNode.class);

//    CONVERT JSON OBJECT BACK TO EMPLOYEE OBJECT
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode,Employee.class);
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId)
    {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee==null)
            throw new RuntimeException("id is not found");

        employeeService.deleteById(employeeId);
        return "Deleted employee"+employeeId;

    }
}
