package io.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {

    @Value("${my.greeting: default value here }")
    private String greetingMsg;

    @Value("some static msg here")
    private String staticMsg;

    @Value("${my.list.value}")
    private List<String> listValues;

    @Value("#{${dbValues}}")
    private Map<String, String> keyValuePair;

    @Autowired
    private DBSettings dbSettings;

    @Autowired
    private Environment env;

    @GetMapping("/greeting")
    public String greeting(){
        //return greetingMsg + staticMsg + listValues + keyValuePair;
        return dbSettings.getConnection() + dbSettings.getHost();
    }

    @GetMapping("/envDetails")
    public String getEnvDetails(){
        return env.toString();
    }
}
