package contractor.contracts.postcondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method that has been annotated {@code Postcondition} will evaluated its {@code PostconditionContract}
 * after the method has been run.
 * @author Ryan Plessner
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Postcondition
{
    /**
     * @return Returns the {@code PostconditionContract} that should be evaluated on the annotated method.
     */
    public Class<? extends PostconditionContract<?,?>> contract();
}