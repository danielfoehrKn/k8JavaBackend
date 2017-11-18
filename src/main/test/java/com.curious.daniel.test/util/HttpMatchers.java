package com.curious.daniel.test.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;

public class HttpMatchers {
    public HttpMatchers() {
    }

    public static Matcher<Response> hasStatus(final Response.Status status) {
        return new BaseMatcher<Response>() {
            public boolean matches(Object item) {
                Response response = (Response)item;
                return status.getStatusCode() == response.getStatus();
            }

            public void describeTo(Description description) {
                description.appendText("Status was expected to be ").appendValue(status.getStatusCode());
            }
        };
    }

    public static Matcher<Response> isOk() {
        return hasStatus(Response.Status.OK);
    }

    public static Matcher<Response> isNoContent() {
        return hasStatus(Response.Status.NO_CONTENT);
    }

    public static Matcher<Response> isCreated() {
        return hasStatus(Response.Status.CREATED);
    }

    public static Matcher<Response> isNotFound() {
        return hasStatus(Response.Status.NOT_FOUND);
    }

    public static Matcher<Response> isBadRequest() {
        return hasStatus(Response.Status.BAD_REQUEST);
    }

    public static Matcher<Response> hasStatusFamily(final Response.Status.Family statusFamily) {
        return new BaseMatcher<Response>() {
            public boolean matches(Object item) {
                Response response = (Response)item;
                return statusFamily == response.getStatusInfo().getFamily();
            }

            public void describeTo(Description description) {
                description.appendText("StatusFamily was expected to be ").appendValue(statusFamily);
            }
        };
    }

    public static Matcher<Response> isSuccessful() {
        return hasStatusFamily(Response.Status.Family.SUCCESSFUL);
    }
}