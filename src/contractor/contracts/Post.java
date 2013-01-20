package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * A method that has been annotated {@code Post} will evaluated its
 * {@code Contract} after the method has been run.
 * <p>
 * The contract will be given the return value of the method to evaluate.
 * As such, this annotation should only be placed on methods that have return values.
 * 
 * @author Ryan Plessner
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Post {
    
    /**
     * @return Returns the {@code Postcondition} that should be
     *         evaluated on the annotated method.
     */
    public Class<? extends Contract<?>> value();
}