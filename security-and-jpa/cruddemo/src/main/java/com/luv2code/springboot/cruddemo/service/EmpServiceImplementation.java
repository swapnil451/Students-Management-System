package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dao.EmployeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*This is used to write the business logic
Many times the information we want about a employee is stored in different DAO ie we can access using the
 methods of different classes implemented using different DAO.
 Like Employee and EmpDaoimplementation can only give basic personal details about a person , another implementation
 of some other DAO can give salary , other  can give skills then another class can give skills detials
 DAO 1 - A,B,C methods
 DAO 2 - D,E,F methods
 DAO 3 - G,H,I methods
 in our application we only want A,E,F,H methods then we will only write these methods in service
 */
@Service
public class EmpServiceImplementation implements EmployeeService {

    EmployeeRepository ER;

    @Autowired
    EmpServiceImplementation (EmployeeRepository ER)
    {
        this.ER=ER;
    }

    @Override
    public List<Employee> findAll() {
        return ER.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = ER.findById(theId);

        Employee theEmployee;
        if (result.isPresent())
            theEmployee=result.get();
        else
            throw new RuntimeException("we could find the employee");


        return theEmployee;
    }


    @Override
    public Employee SaveEmployee(Employee theEmployee) {
        return ER.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        ER.deleteById(theId);
    }
    
}
