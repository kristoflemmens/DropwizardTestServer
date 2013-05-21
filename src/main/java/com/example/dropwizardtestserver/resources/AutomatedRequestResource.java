package com.example.dropwizardtestserver.resources;

import com.example.dropwizardtestserver.core.AutomatedRequestQueue;

import javax.jms.JMSException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/request")
public class AutomatedRequestResource {

    private final AutomatedRequestQueue queue;

    public AutomatedRequestResource(AutomatedRequestQueue queue) {
        this.queue = queue;
    }

    @POST
    @Path("/automated")
    public void submit(String automatedRequest) {
        try {
            queue.publish(automatedRequest);
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
