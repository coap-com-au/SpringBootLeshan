package org.eclipse.leshan.server.bootstrap.demo;

import org.eclipse.leshan.server.bootstrap.EditableBootstrapConfigStore;
import org.eclipse.leshan.server.bootstrap.demo.servlet.BootstrapServlet;
import org.eclipse.leshan.server.bootstrap.demo.servlet.ServerServlet;
import org.eclipse.leshan.server.californium.impl.LeshanBootstrapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;
import java.security.cert.X509Certificate;

@Configuration
public class WebConfig {
    @Autowired
    private EditableBootstrapConfigStore bsStore;

    @Bean
    public ServletRegistrationBean<HttpServlet> bootstrapServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new BootstrapServlet(bsStore));
        servRegBean.addUrlMappings("/api/bootstrap/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }

    @Autowired
    private LeshanBootstrapServer bsServer;

    @Autowired
    private X509Certificate serverCertificate;
    ;

    @Bean
    public ServletRegistrationBean<HttpServlet> serverpServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new ServerServlet(bsServer, serverCertificate));
        servRegBean.addUrlMappings("/api/server/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }


}
