package com.port.portlistener;

import org.apache.catalina.connector.Connector;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TomcatConfig {

    @Value("${server.additionalPorts}")
    private String additionalPorts;

    @Bean
    public TomcatServletWebServerFactory getApplicationContext() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector[] additionalConnectors = additionalConnector();
        if (additionalConnectors != null && additionalConnectors.length > 0) {
            tomcat.addAdditionalTomcatConnectors(additionalConnectors);
        }
        return tomcat;
    }

    private Connector[] additionalConnector() {
        if (Strings.isBlank(additionalPorts)) {
            return null;
        }
        String[] ports = this.additionalPorts.split(",");
        List<Connector> list = new ArrayList<>();
        for (String port : ports) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.parseInt(port));
            list.add(connector);
        }
        return list.toArray(new Connector[] {});
    }
}
