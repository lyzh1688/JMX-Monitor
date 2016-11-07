package com.yuezy.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by 胡志洁 on 2016/11/4.
 */
@Configuration
@ConfigurationProperties(locations = "classpath:jmx.properties" ,prefix = "service.jmx")
public class JMXConnConfiguration {

    private String rmi;

    public String getRmi() {
        return rmi;
    }

    public void setRmi(String rmi) {
        this.rmi = rmi;
    }

    @Bean(name = "mBeanServerConnection")
    public MBeanServerConnection mBeanServerConnection(){
        JMXServiceURL ServiceURL = null;
        JMXConnector jmxConnector = null;
        try {
            ServiceURL = new JMXServiceURL(this.rmi);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL);
            return jmxConnector.getMBeanServerConnection();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }
}
