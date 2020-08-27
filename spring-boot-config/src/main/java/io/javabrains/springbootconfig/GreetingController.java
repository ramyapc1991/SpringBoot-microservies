package io.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    @Value("${my.greeting: default value here }")
    private String greetingMsg;

    @Value("some static msg here")
    private String staticMsg;

    @Value("${my.list.value}")
    private List<String> listValues;

    @Value("#{${dbValues}}")
    private Map<String, String> keyValuePair;

    @RequestMapping("greeting")
    public String greeting(){
        return greetingMsg + staticMsg + listValues + keyValuePair;
    }
}
