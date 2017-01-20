package org.rt.checks.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker annotation for ignore concrete checks
 *
 * @author dsaponenko
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RtCheckIgnore {
}
