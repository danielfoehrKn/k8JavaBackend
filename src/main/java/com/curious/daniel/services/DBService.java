package com.curious.daniel.services;

import com.curious.daniel.dao.NewsDAO;
import com.curious.daniel.dto.DTOMapper;
import com.curious.daniel.dto.NewsDTO;
import com.curious.daniel.entities.News;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;



@Api("Database Service")
@Path("/db")


public class DBService {

    @Inject
    private NewsDAO dao;
    


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String checkDb() {
        getNews();
        return "Db up and running";
    }

    @GET
    @Path("news")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NewsDTO> getNews() {
        List<News> all = dao.findAll();
        return DTOMapper.mapCollection(all, DTOMapper::mapNews);
    }


    @POST
    @Path("news")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public NewsDTO createNews(NewsDTO dto) {

        News entity = new News().setTitle(dto.getTitle())
                .setDescription(dto.getDescription());
        
        dao.persist(entity);

        return DTOMapper.mapNews(entity);
    }


}
