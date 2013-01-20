package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is meant to be placed on interfaces for classes that will
 * have contracts.
 * <p>
 * The {@code ContractEnforcer} will only look at the methods from contracted
 * interfaces for {@code Pre}s and {@code Post}s.
 * 
 * @author Ryan Plessner
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Contracted {}