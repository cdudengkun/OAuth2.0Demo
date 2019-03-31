package com.cjack.oauth.client;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by root on 5/28/18.
 */
@SpringBootApplication
@ComponentScan("com.cjack.oauth.client")
public class OauthClientApplication {

    private static Logger logger = Logger.getLogger( OauthClientApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder( OauthClientApplication.class).web(true).run(args);
        logger.info("Application started");
    }
}
