package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation can be placed on parameters in order to enforce contracts.
 * <p>
 * The Class that is provided as a contract will create a be instantiated to
 * enforce the precondition.
 * 
 * @author Ryan Plessner
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Pre {
    /**
     * @return Returns the {@code PreconditionContract} that should be
     *         evaluated on the annotated method.
     */
    public Class<? extends Contract<?>> value();
}