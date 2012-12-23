package contractor.enforcement;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import contractor.contracts.ContractEvaluation;
import contractor.contracts.postcondition.PostconditionContract;
import contractor.contracts.precondition.Precondition;
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
        // TODO: check Preconditions using InvarianceBundle
        checkPrecondition(method, args);
        
        Object result = method.invoke(implementation, args);
        checkPostconditions(bundle, result);
        
        if(!accessable) {
            method.setAccessible(false);
        }
        
        return result;
    }
    
    private void checkPostconditions(InvarianceBundle bundle, Object result) {
	for(PostconditionContract postcondition : bundle.getPostconditions()) {
	    ContractEvaluation eval = postcondition.evaluate(result);
	    if(!eval.successful()) {
		throw new RuntimeException(eval.getError());
	    }
	}
    }
    /*
    private void checkPreconditions(Method method, Object[] args, InvarianceBundle bundle) {
	for(List<PreconditionContract> preconditions : bundle.getPreconditions()) {
	    for(PreconditionContract precondition : preconditions) {
		precondition.setCurrent(type)
	    }
	}
    }
    */
    private void checkPrecondition(Method method, Object[] args) {
        int argIndex = 0;
        for(Annotation[] annos : method.getParameterAnnotations()) {
            for(Annotation anno : annos) {
                Class<?> preCondClazz = ((Precondition) anno).contract();
                try
                {
                    Object instance = preCondClazz.newInstance();
                    @SuppressWarnings("unchecked")
                    PreconditionContract<Object,Object> c = (PreconditionContract<Object,Object>) instance;
                    c.setCurrent(implementation);
                    ContractEvaluation<Object> eval = c.evaluate(args[argIndex]);
                    if(!eval.successful()) {
                        throw new RuntimeException(eval.getError());
                    }
                } 
                catch (InstantiationException e)
                {
                    throw new RuntimeException("Unable to instantiate a Precondition contract " + preCondClazz.getName() +".", e);
                } catch (IllegalAccessException e)
                {
                    throw new RuntimeException("Unable to access a Precondition's constructor " + preCondClazz.getName() +".", e);
                } 

            }
            argIndex++;
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
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    private Object handleTypeObject(Object proxy, Method method, Object[] args) {
        String name = method.getName();
        if(EQUALS.equals(name)) {
            return proxy == args[0];
        } else if(HASHCODE.equals(name)) {
            return System.identityHashCode(proxy);
        } else if(TOSTRING.equals(name)) {
            return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy)) + ", with InvocationHandler " + this;
        } else {
            throw new IllegalStateException(String.valueOf(method));
        }
    }
}