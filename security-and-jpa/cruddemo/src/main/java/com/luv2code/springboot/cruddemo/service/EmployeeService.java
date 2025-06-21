package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.entity.Employee;

import java.util.List;
/*This is used to write the business logic
Many times the information we want about a employee is stored in different branches ie we can access using the
 methods of different classes
 Like Employee and EmpDaoimplementation can only give basic personal details about a person , another class can
 give salary , other class can give skills then another class can give skills detials */

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(int theId);

    Employee SaveEmployee(Employee theEmployee);

    void deleteById(int theId);
}
