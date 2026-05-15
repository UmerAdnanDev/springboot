package com.example.topic1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component // This class will be managed by Spring
public class MyApp {
    // Demonstrating DEPENDENCY INJECTION
    // Spring will automatically provide these dependencies when creating MyApp
    private final ComponentAnnotation CompA;  // This is a bean (@Component present)
    private final NotBeanClass NotB;          // This is not a bean by default
    
    // @Autowired - Tells Spring to inject dependencies through constructor
    @Autowired
    public MyApp(ComponentAnnotation CompA, NotBeanClass NotB){
        this.CompA = CompA;  // Spring finds the ComponentAnnotation bean automatically
        this.NotB = NotB;    // Spring finds NotBeanClass ONLY because of MyAppConfig
    }
    public void run1(){
        System.out.println();
        System.out.println("--Constructor Dependency Injection--");
        CompA.Msg(); // Using injected dependency
        System.out.println();
    }
    public void run2(){
        System.out.println();
        System.out.println("--Constructor Dependency Injection Configured as Bean--");
        NotB.Msg(); // Using injected dependency that was configured as a Bean
        System.out.println();
    }
}
