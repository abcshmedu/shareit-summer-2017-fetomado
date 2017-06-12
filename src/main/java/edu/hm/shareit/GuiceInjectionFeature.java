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

@Provider
public class GuiceInjectionFeature implements Feature {

    private static final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).to(MediaServiceImpl.class);
            bind(CopyService.class).to(CopyServiceImpl.class);
            bind(UserService.class).to(UserServiceImpl.class);
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
    static Injector getInjectorInstance() {
        return injector;
    }

}
