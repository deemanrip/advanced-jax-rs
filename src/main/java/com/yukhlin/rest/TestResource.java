package com.yukhlin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;

@Path("test")
public class TestResource {

    @GET
    @Produces(value = { MediaType.TEXT_PLAIN, "text/shortdate" })
    public Date test() {
        return Calendar.getInstance().getTime();
    }

}