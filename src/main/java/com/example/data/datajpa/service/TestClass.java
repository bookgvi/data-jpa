package com.example.data.datajpa.service;

import com.example.data.datajpa.annotation.Logging;
import org.springframework.stereotype.Component;

@Component
public class TestClass {
    @Logging
    public void method1() {
        System.out.println("TestClass - method1");
        method2();
    }

    @Logging
    public void method2() {
        System.out.println("Test class - method2");
    }
}
