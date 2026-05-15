package com.example.topic1;
import org.springframework.stereotype.Component;
@Component //Tells Spring: "Create and manage a single instance of this class"
// Spring will automatically detect this class during component scanning
// The bean name will be "componentAnnotation" (camelCase of class name)
public class ComponentAnnotation {
    public ComponentAnnotation(){
      // this will run earlier when spring creates bean of this class automatically during start up
        System.out.println("-- Msg from @Component class auto initialized object --");
    }
    public void Msg(){
        System.out.println("-- This msg is a method called from @component class named ComponentAnnotation --");
    }
}
