package com.example.topic1;
// No @Component annotation - Spring will not manage this class
// You must create instances manually with 'new' keyword
public class ManualComponent {
    public ManualComponent(){
        System.out.println("-- Msg from the Manual Component --");
    }
    public void Msg(){
        System.out.println("-- This msg is a method called from manual component class --");
    }
}