package com.curious.daniel.test.tests;

import com.curious.daniel.test.ApiTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.curious.daniel.test.util.HttpMatchers.isSuccessful;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusServiceTest extends ApiTest {

    private String TARGET_PATH;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TARGET_PATH = "/status";
    }

    @Test
    public void status() throws Exception {

        Response response = target(TARGET_PATH).request().get();
        assertThat(response, isSuccessful());
    }
}
