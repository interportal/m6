package md.curs;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import md.curs.soap.SoapUserService;

import javax.xml.ws.Endpoint;

/**
 * Created by MG
 */
@Configuration
public class SoapConfig {

    /**
     * This would be on the client side
     * @return the proxy service
     */
//    @Bean
//    public SoapUserService client() {
//        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
//        bean.setServiceClass(SoapUserService.class);
//        bean.setAddress("https://localhost:8080/soap/user");
//
//        return (SoapUserService) bean.create();
//    }

    @Bean
    public Endpoint userEndpoint(Bus bus, SoapUserService service) {
        EndpointImpl endpoint = new EndpointImpl(bus, service);
        endpoint.publish("/user");
        return endpoint;
    }
}
