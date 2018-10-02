package ca.eloas.presentations.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.WebServiceProvider;

@Configuration
public class MyConfiguration {

    @Bean
    EvenGooderFields createFields() {

        return new EvenGooderFields(new UsedClassOne(), new UsedClassTwo());
    }

    // or
    @Bean
    EvenGooderFields createFields(UsedClassOne one, UsedClassTwo two) {

        return new EvenGooderFields(one, two);
    }
}
