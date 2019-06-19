package com.flchen.practice.service;

import com.flchen.practice.beans.Bean;

@Bean
public class SalaryService {

    public Integer salary(Integer price, Integer experience) {

        return price * experience;
    }
}
