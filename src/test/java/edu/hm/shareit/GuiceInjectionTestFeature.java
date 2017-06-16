package edu.hm.shareit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.hm.shareit.persistence.Persistence;
import edu.hm.shareit.services.CopyService;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.UserService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import static org.mockito.Mockito.mock;

public class GuiceInjectionTestFeature implements Feature {

    private static final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).toInstance(mock(MediaService.class));
            bind(CopyService.class).toInstance(mock(CopyService.class));
            bind(UserService.class).toInstance(mock(UserService.class));
            bind(Persistence.class).toInstance(mock(Persistence.class));
            bind(SessionFactory.class).toInstance(
                    new Configuration()
                            .configure()
                            .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:shareit-test-db")
                            .buildSessionFactory());
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
