package com.shamy1st.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.security.auth.callback.CallbackHandler;
import java.util.Collections;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet message = new MessageDispatcherServlet();
        message.setApplicationContext(context);
        message.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(message, "/ws/*");
    }

    // expose url -> /ws/courses.wsdl
    @Bean(name="courses")
    public DefaultWsdl11Definition wsdlDefinition(XsdSchema coursesSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("CoursePort");
        wsdl.setTargetNamespace("http://shamy1st.com/courses");
        wsdl.setLocationUri("/ws");
        wsdl.setSchema(coursesSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema courseSchema() {
        return new SimpleXsdSchema(new ClassPathResource("course.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
        interceptor.setCallbackHandler(callbackHandler());
        interceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return interceptor;
    }

    private SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap("ahmed", "1234"));
        return handler;
    }
}