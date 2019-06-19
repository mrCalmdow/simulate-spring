package com.flchen.practice.controller.mo;

/**
 * author fl.chen
 * Date 2019-06-15
 * Time 10:55
 **/
public class EmployeeMO {
    private String name;

    private Integer experience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        int salary = 1000 * experience;
        return this.name + this.experience + " -- " + salary;
    }
}
