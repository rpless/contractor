package contractor.enforcement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import contractor.contracts.postcondition.Postcondition;
import contractor.contracts.postcondition.PostconditionContract;
import contractor.contracts.precondition.Precondition;
import contractor.contracts.precondition.PreconditionContract;

/**
 * An {@code InvarianceBundle} is a bundle of invariants for a given method.
 * @author Ryan Plessner
 *
 */
@SuppressWarnings("rawtypes")
class InvarianceBundle
{
    private final String methodName;
    private final List<List<PreconditionContract>> preconditions;
    private final List<PostconditionContract> postconditions;
    
    static InvarianceBundle createFromMethod(Method method) {
        return new InvarianceBundle(method.getName(), getPreconditions(method), getPostconditions(method));
    }
    
    private static List<List<PreconditionContract>> getPreconditions(Method method) {
	List<List<PreconditionContract>> preconditions = new LinkedList<>();
	for(Annotation[] annotations : method.getParameterAnnotations()) {
	    List<PreconditionContract> argConditions = new LinkedList<>();
	    for(Annotation annotation : annotations) {
		if(annotation instanceof Precondition) {
		    try {
			argConditions.add(((Precondition) annotation).contract().newInstance());
		    } catch (InstantiationException e) {
			throw new RuntimeException("Unable to instantiate a Precondition contract " + annotation.getClass().getName() +".", e);
		    } catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to access a Precondition's constructor " + annotation.getClass().getName() +".", e);
		    } 
		}
	    }
	    preconditions.add(argConditions);
	}
	return preconditions;
    }

    private static List<PostconditionContract> getPostconditions(Method method) {
	List<PostconditionContract> postconditions = new LinkedList<>();
	for(Annotation anno : method.getAnnotations())
	{
	    if(anno instanceof Postcondition) {
		try {
		    postconditions.add(((Postcondition) anno).contract().newInstance());
		} catch (InstantiationException e) {
                    throw new RuntimeException("Unable to instantiate a Precondition contract " + anno.getClass().getName() +".", e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access a Precondition's constructor " + anno.getClass().getName() +".", e);
                } 
		
	    }
	}
	return postconditions;
    }
    
    private InvarianceBundle(String methodName, List<List<PreconditionContract>> preconditions, List<PostconditionContract> postconditions) {
	this.methodName = methodName;
	this.preconditions = preconditions;
	this.postconditions = postconditions;
    }

    List<List<PreconditionContract>> getPreconditions() {
        return preconditions;
    }

    List<PostconditionContract> getPostconditions() {
        return postconditions;
    }

    public String toString() {
	return "Invariants for " + methodName;
    }
}