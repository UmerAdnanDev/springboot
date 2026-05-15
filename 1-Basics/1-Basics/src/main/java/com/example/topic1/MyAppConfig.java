package com.example.topic1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration// This is an alternative to using @Component - manual bean registration
public class MyAppConfig {
    @Bean // This acts as a bridge makes regular class into Spring accessable class
    // This allows NotBeanClass (which has no @Component) to be used with @Autowired
    public NotBeanClass notBeanClass(){
      // Spring calls your @Bean method once, saves the result, and gives everyone that same saved copy.
      //  This is called a Singleton (one instance)
        return new NotBeanClass();
    }
}