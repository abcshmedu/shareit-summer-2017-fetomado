package edu.hm.shareit.resources;

import edu.hm.shareit.services.CopyService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Application;

public class CopyResourceTest extends JerseyTest {

    @Mock
    private CopyService serviceMock;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new CopyResource(serviceMock));
    }

    @Test
    public void test() {
        assert(true);
    }

}
