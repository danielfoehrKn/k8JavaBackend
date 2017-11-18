package com.curious.daniel.test.tests;

import com.curious.daniel.dto.NewsDTO;
import com.curious.daniel.entities.News;
import com.curious.daniel.test.ApiTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.curious.daniel.test.util.HttpMatchers.isSuccessful;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DbServiceTest extends ApiTest {

    private String TARGET_PATH;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TARGET_PATH = "/db";
    }


    @Test
    public void dbStatus() throws Exception {
        News news = new News().setTitle("Hi").setDescription("bla");
        db.transactional(entityManager -> entityManager.persist(news));
        
        Response response = target(TARGET_PATH).request().get();
        assertThat(response, isSuccessful());
    }

    @Test
    public void getNews() throws Exception {
        News news = new News().setTitle("Hi").setDescription("bla");
        db.transactional(entityManager -> entityManager.persist(news));

        Response response = target(TARGET_PATH).path("news").request().get();
        assertThat(response, isSuccessful());

        List<NewsDTO> dto = response.readEntity(new GenericType<List<NewsDTO>>() {
        });

        // verify
        assertThat(dto, hasSize(1));
        assertThat(dto, hasItem(hasProperty("title", is(news.getTitle()))));
    }
}
