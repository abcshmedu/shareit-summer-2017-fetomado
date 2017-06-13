package edu.hm.shareit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.hm.shareit.services.*;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import static org.mockito.Mockito.mock;

@Provider
public class GuiceInjectionTestFeature implements Feature {

    private static final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).toInstance(mock(MediaService.class));
            bind(CopyService.class).toInstance(mock(CopyService.class));
            bind(UserService.class).toInstance(mock(UserService.class));
        }
    });

    public boolean configure(FeatureContext context) {
        ServiceLocator serviceLocator = ServiceLocatorProvider.getServiceLocator(context);
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(getInjectorInstance());
        return true;
    }

    /**
     * This method is only required for the HK2-Guice-Bridge in the Application class.
     * @return Injector instance.
     */
    public static Injector getInjectorInstance() {
        return injector;
    }

}