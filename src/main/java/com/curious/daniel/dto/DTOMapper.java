package com.curious.daniel.dto;


import com.curious.daniel.entities.News;
import com.curious.daniel.entities.Repositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DTOMapper {

    private static final Logger log = LoggerFactory.getLogger(DTOMapper.class);

    public static <ENTITY, DTO> List<DTO> mapCollection(Collection<ENTITY> entities, Function<ENTITY, DTO> mapAlert) {
        return entities.stream().map(mapAlert).collect(Collectors.toList());
    }

    // ================================================================================
    // Entities
    // ================================================================================
    
    public static NewsDTO mapNews(News news) {

        return new NewsDTO()
                .setId(news.getId())
                .setDescription(news.getDescription())
                .setTitle(news.getTitle());
    }

    public static RepositoriesDTO mapRepositories(Repositories repositories) {

        return new RepositoriesDTO()
                .setId(repositories.getId());
    }

}
