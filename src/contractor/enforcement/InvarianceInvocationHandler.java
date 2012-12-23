package contractor.enforcement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import contractor.contracts.ContractEvaluation;
import contractor.contracts.postcondition.PostconditionContract;
import contractor.contracts.precondition.PreconditionContract;

/**
 * The {@link InvarianceInvocationHandler} acts as a listener that can be created for objects with 
 * Invariants.
 * @author Ryan Plessner
 *
 */
@SuppressWarnings({"rawtypes"})
class InvarianceInvocationHandler implements InvocationHandler
{
    private static final String EQUALS = "equals";
    private static final String HASHCODE = "hashCode";
    private static final String TOSTRING = "toString";
    
    private Object implementation;
    private HashMap<String, InvarianceBundle> contracts;
    
    InvarianceInvocationHandler(Object implementation) {
        this.implementation = implementation;
        this.contracts = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if(Object.class == method.getDeclaringClass()) {
            return handleTypeObject(proxy, method, args);
        }
        InvarianceBundle bundle = getInvarianceBundle(method);
        
        boolean accessable = method.isAccessible();
        if(!accessable) {
            method.setAccessible(true);
        }
        checkPreconditions(bundle, args);
        Object result = method.invoke(implementation, args);
        checkPostconditions(bundle, result);
        
        if(!accessable) {
            method.setAccessible(false);
        }
        
        return result;
    }
    
    private void checkPreconditions(InvarianceBundle bundle, Object[] args) {
	int argIndex = 0;
	for(List<PreconditionContract> preconditions : bundle.getPreconditions()) {
	    for(PreconditionContract precondition : preconditions) {
		handleContractEvaluations(precondition.evaluate(args[argIndex]));
	    }
	}
    }
    
    private void checkPostconditions(InvarianceBundle bundle, Object result) {
	for(PostconditionContract postcondition : bundle.getPostconditions()) {
	    handleContractEvaluations(postcondition.evaluate(result));
	}
    }
    
    private void handleContractEvaluations(ContractEvaluation evaluation) {
	if(!evaluation.successful()) {
	    throw new RuntimeException(evaluation.getError());
	}
    }
    
    private InvarianceBundle getInvarianceBundle(Method method) {
	String key = method.getName();
	if (contracts.containsKey(key)) {
	    return contracts.get(key);
	} else {
	    InvarianceBundle bundle = InvarianceBundle.createFromMethod(method);
	    contracts.put(key, bundle);
	    return bundle;
	}
    }

    /**
     * Handle the special case of when the method is defined in Object and has not been overridden.
     * @param proxy The Proxy object.
     * @param method The method that is defined by object.
     * @param args The arguments to the method.
     * @return The result of the method call.
     */
    private Object handleTypeObject(Object proxy, Method method, Object[] args) {
        String name = method.getName();
        if(EQUALS.equals(name)) {
            return proxy == args[0];
        } else if(HASHCODE.equals(name)) {
            return System.identityHashCode(proxy);
        } else if(TOSTRING.equals(name)) {
            return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy)) + ", with Invariance Handler " + this;
        } else {
            throw new IllegalStateException(String.valueOf(method));
        }
    }
}