package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation can be placed on the parameters of methods in order to enforce contracts.
 * <p>
 * The contract will be given the argument value when the method is called.
 * 
 * @author Ryan Plessner
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Pre {
    
    /**
     * @return Returns the {@code Contract} that should be evaluated on the annotated method.
     */
    public Class<? extends Contract<?>> value();
}