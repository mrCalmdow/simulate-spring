package com.flchen.practice.controller;

import com.flchen.practice.beans.Autowired;
import com.flchen.practice.controller.mo.EmployeeMO;
import com.flchen.practice.web.mvc.Controller;
import com.flchen.practice.web.mvc.RequestMapping;
import com.flchen.practice.web.mvc.RequestParam;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 20:45
 **/

@Controller
public class SalaryController {

    @Autowired
    SalaryController salaryController;

    @RequestMapping("/getSalary")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {

        return 1000;
    }

    @RequestMapping("/createEmployee")
    public EmployeeMO getEmployeeInfo(@RequestParam("name") String name, @RequestParam("experience") Integer experience) {
        EmployeeMO employeeMO = new EmployeeMO();
        employeeMO.setName(name);
        employeeMO.setExperience(experience);
        System.out.println(employeeMO.toString());
        return employeeMO;
    }
}
