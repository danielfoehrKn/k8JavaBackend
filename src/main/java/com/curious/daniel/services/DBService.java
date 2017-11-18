package com.curious.daniel.services;

import com.curious.daniel.dao.NewsDAO;
import com.curious.daniel.dto.DTOMapper;
import com.curious.daniel.dto.NewsDTO;
import com.curious.daniel.entities.News;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/db")
public class DBService {

    @Inject
    private EntityManager em;

    @Inject
    private NewsDAO dao;
    


    @GET
    public String checkDb() {
        getNews();
        return "Db up and running";
    }

    @GET
    @Path("news")
    public List<NewsDTO> getNews() {
        List<News> all = dao.findAll();
        return DTOMapper.mapCollection(all, DTOMapper::mapNews);
    }


    @POST
    @Path("news")
    public NewsDTO createNews(NewsDTO dto) {

        News entity = new News().setTitle(dto.getTitle())
                .setDescription(dto.getDescription());
        
        dao.persist(entity);

        return DTOMapper.mapNews(entity);
    }


}
