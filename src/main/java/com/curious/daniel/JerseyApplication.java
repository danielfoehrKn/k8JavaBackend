package com.curious.daniel;

import com.curious.daniel.services.StatusService;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        
        packages("com.curious.daniel.services");

        // Services   or register whole package : packages("com.abc.jersey.services");
        register(StatusService.class);

        // Swagger
//        register(SwaggerSerializers.class);
//        register(ApiListingResource.class);
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setResourcePackage(getClass().getPackage().getName());
//        beanConfig.setPrettyPrint(true);
//        beanConfig.setScan(true);
//        beanConfig.setBasePath("/api");




    }


}
