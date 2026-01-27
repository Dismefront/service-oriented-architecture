package org.dismefront.soap.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.dismefront.soap.NavigatorSoapServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.Endpoint;

@Configuration
public class SoapEndpointsConfig {

    @Bean
    public Endpoint navigatorSoapEndpoint(Bus bus, NavigatorSoapServiceImpl implementor) {
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/NavigatorService");
        return endpoint;
    }
}

