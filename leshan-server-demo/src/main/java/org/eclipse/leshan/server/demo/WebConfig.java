package org.eclipse.leshan.server.demo;


import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.leshan.server.LwM2mServer;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.demo.servlet.ClientServlet;
import org.eclipse.leshan.server.demo.servlet.EventServlet;
import org.eclipse.leshan.server.demo.servlet.ObjectSpecServlet;
import org.eclipse.leshan.server.demo.servlet.SecurityServlet;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;
import java.security.cert.X509Certificate;

@Configuration
public class WebConfig {


    @Autowired
    private LeshanServer lwServer;

    @Bean
    public ServletRegistrationBean<HttpServlet> eventServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new EventServlet(lwServer, lwServer.getSecuredAddress().getPort()));
        servRegBean.addUrlMappings("/event/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }


    @Bean
    public ServletRegistrationBean<HttpServlet> clientServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new ClientServlet(lwServer));
        servRegBean.addUrlMappings("/api/clients/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }


    @Autowired
    private EditableSecurityStore store;
    @Autowired
    private X509Certificate serverCertificate;

    @Bean
    public ServletRegistrationBean<HttpServlet> securityServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new SecurityServlet(store, serverCertificate));
        servRegBean.addUrlMappings("/api/security/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }


    @Bean
    public ServletRegistrationBean<HttpServlet> objectServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new ObjectSpecServlet(lwServer.getModelProvider(), lwServer.getRegistrationService()));
        servRegBean.addUrlMappings("/api/objectspecs/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }


}
