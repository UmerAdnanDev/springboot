package com.example.topic1;
// No @Component annotation - not automatically detected by Spring
// This demonstrates you can use classes you don't own/can't modify with Spring
public class NotBeanClass {
    public NotBeanClass(){
        System.out.println("-- A class without @Component meaning Bean not created --");
    }
    public void Msg(){
        System.out.println("-- Msg from NotBeanClass --");
    }
}