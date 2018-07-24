package com.curious.daniel.services;

import com.curious.daniel.JerseyApplication;
import com.curious.daniel.dao.NewsDAO;
import com.curious.daniel.dao.RepositoryDAO;
import com.curious.daniel.dto.DTOMapper;
import com.curious.daniel.dto.NewsDTO;
import com.curious.daniel.dto.RepositoriesDTO;
import com.curious.daniel.entities.News;
import com.curious.daniel.entities.Repositories;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


@Api("Database Service")
@Path("/db")


public class DBService {

    private static final Logger log = LoggerFactory.getLogger(JerseyApplication.class);


    @Inject
    private NewsDAO dao;


    @Inject
    private RepositoryDAO repositoryDAO;

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

    @POST
    @Path("repositories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RepositoriesDTO createRepositories(RepositoriesDTO dto) {
        Repositories entity = new Repositories().setId(dto.getId());
        repositoryDAO.persist(entity);
        return DTOMapper.mapRepositories(entity);
    }

    @GET
    @Path("repositories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<RepositoriesDTO> getAllRegisteredRepositories() {
        List<Repositories> all = repositoryDAO.findAll();
        return DTOMapper.mapCollection(all, DTOMapper::mapRepositories);
    }

    @GET
    @Path("repositories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RepositoriesDTO getRegisteredRepositoryById(@PathParam("id") String id) {
        log.info("Searching for repository with id " + id);
        Repositories repo = repositoryDAO.find(id).orElseThrow(() -> new NotFoundException(Repositories.class.getSimpleName()));
        return DTOMapper.mapRepositories(repo);
    }
}
