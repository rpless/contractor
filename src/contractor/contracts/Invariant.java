package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is meant to be placed on interfaces for classes that will have invariants.
 * <p>
 * The {@code InvariantEnforcer} will only look at the methods from invariant interfaces for {@code Precondition}s and {@code Postcondition}s.
 * @author Ryan Plessner
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Invariant {}