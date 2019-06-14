package com.flchen.practice.controller;

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

    @RequestMapping("/getSalary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {

        return 1000;
    }
}
