package io.javabrains.springbootconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("db")
public class DBSettings {
    private String connection;
    private String host;
    private int port;

}
