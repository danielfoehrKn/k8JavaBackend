package com.curious.daniel.services;

import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Api("Status Service")
@Path("/status")


@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class StatusService {


    @Context
    UriInfo context;

    @GET
    public String getIt() {
        //CLOUDSQL_INSTANCE_CONNECTION_NAME=k8blackwater:europe-west1:blackwatersql
        //CLOUDSQL_DATABASE_NAME=blackwaterdb
        return "Serving at external ip: " + context.getBaseUri() +
                "| Pod IP: " + System.getenv("MY_POD_IP")
                + " | POD Name: " + System.getenv("MY_POD_NAME")
                + " | Pod Namespace: " + System.getenv("MY_POD_NAMESPACE")
                + " | Node Name : " + System.getenv("MY_NODE_NAME")
                + " | Pod Service Account: " + System.getenv("MY_POD_SERVICE_ACCOUNT")
                + " | CLOUDSQL_INSTANCE_CONNECTION_NAME: " + System.getenv("CLOUDSQL_INSTANCE_CONNECTION_NAME")
                + " | CLOUDSQL_INSTANCE_CONNECTION_NAME: " + System.getenv("CLOUDSQL_INSTANCE_CONNECTION_NAME")
                + " | DB_PASSWORD: " + System.getenv("DB_PASSWORD")
                + " | DB_USER: " + System.getenv("DB_USER");
    }
}
