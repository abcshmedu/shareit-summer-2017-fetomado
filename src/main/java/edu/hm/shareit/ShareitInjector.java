package edu.hm.shareit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.hm.shareit.services.*;

/**
 * Context Listener to enable usage of google guice together with jersey.
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public class ShareitInjector {

    private static final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).to(MediaServiceImpl.class);
            bind(CopyService.class).to(CopyServiceImpl.class);
            bind(UserService.class).to(UserServiceImpl.class);
        }
    });

    /**
     * This method is only required for the HK2-Guice-Bridge in the Application class.
     * @return Injector instance.
     */
    static Injector getInjectorInstance() {
        return injector;
    }

}
