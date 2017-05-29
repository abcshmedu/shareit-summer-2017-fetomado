package edu.hm.shareit.resources;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to check the token. It can annotate a path.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {
}
