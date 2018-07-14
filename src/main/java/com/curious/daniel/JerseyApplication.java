package com.curious.daniel;

import com.curious.daniel.dao.NewsDAO;
import com.curious.daniel.entities.EMFFactory;
import com.curious.daniel.entities.EMFactory;
import com.curious.daniel.services.DBService;
import com.curious.daniel.services.StatusService;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.ApplicationPath;


@ApplicationPath("/api")
public class JerseyApplication extends ResourceConfig {

    private static final Logger log = LoggerFactory.getLogger(JerseyApplication.class);
    
    public JerseyApplication() {
        
        packages("com.curious.daniel.services");

        // Services   or register whole package : packages("com.abc.jersey.services");
        register(StatusService.class);
        register(DBService.class);

        // Swagger
        register(SwaggerSerializers.class);
        register(ApiListingResource.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage(getClass().getPackage().getName());
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
        beanConfig.setBasePath("/api");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                // Used for JNDI Lookup
                bind(InitialContext.class).to(Context.class);
                bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
                bindFactory(EMFactory.class).to(EntityManager.class).proxy(true).proxyForSameScope(false).in(RequestScoped.class);


                // Singleton
//                bindFactory(OkHttpClientProvider.class).to(OkHttpClient.class).in(Singleton.class);
//                bindFactory(DestinationConfigurationSupplierProvider.class).to(DestinationConfigurationSupplier.class).in(Singleton.class);



                bind(NewsDAO.class).to(NewsDAO.class);



            }
        });

    }};
