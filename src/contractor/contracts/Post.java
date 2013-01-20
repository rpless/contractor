package contractor.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * A method that has been annotated {@code Post} will evaluated its
 * {@code Postcondition} after the method has been run.
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