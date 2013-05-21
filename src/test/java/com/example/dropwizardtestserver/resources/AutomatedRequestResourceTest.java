package com.example.dropwizardtestserver.resources;

import com.example.dropwizardtestserver.core.AutomatedRequestQueue;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AutomatedRequestResourceTest extends ResourceTest {

    private static final String AUTOMATED_REQUEST = "<xml></xml>";
    @Mock
    private AutomatedRequestQueue queue;

    @Override
    protected void setUpResources() throws Exception {
        addResource(new AutomatedRequestResource(queue));
    }

    @Test
    public void whenAutomatedRequestSubmitted_ThenRequestIsPublishedOnQueue() throws Exception {
        client().resource("/request/automated").post(AUTOMATED_REQUEST);

        verify(queue).publish(AUTOMATED_REQUEST);
    }
}
