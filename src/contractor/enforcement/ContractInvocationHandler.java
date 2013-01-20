package contractor.enforcement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import contractor.contracts.Contract;
import contractor.contracts.ContractEvaluation;

/**
 * The {@link ContractInvocationHandler} acts as a listener that can be
 * created for objects with Contracts.
 * 
 * @author Ryan Plessner
 * TODO
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class ContractInvocationHandler implements InvocationHandler {
    private static final String EQUALS = "equals";
    private static final String HASHCODE = "hashCode";
    private static final String TOSTRING = "toString";

    private Object implementation;
    private HashMap<String, ContractBundle> contracts;

    ContractInvocationHandler(Object implementation) {
        this.implementation = implementation;
        this.contracts = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (Object.class == method.getDeclaringClass()) {
            return handleTypeObject(proxy, method, args);
        }
        ContractBundle bundle = getInvarianceBundle(method);

        boolean accessable = method.isAccessible();
        if (!accessable) {
            method.setAccessible(true);
        }
        checkPreconditions(bundle, args);
        Object result = method.invoke(implementation, args);
        checkPostconditions(bundle, result);

        if (!accessable) {
            method.setAccessible(false);
        }

        return result;
    }

    private void checkPreconditions(ContractBundle bundle, Object[] args) {
        int argIndex = 0;
        for (List<Contract> preconditions : bundle.getPreconditions()) {
            for (Contract precondition : preconditions) {
                handleContractEvaluations(precondition.evaluate(args[argIndex]));
            }
        }
    }

    private void checkPostconditions(ContractBundle bundle, Object result) {
        for (Contract postcondition : bundle.getPostconditions()) {
            handleContractEvaluations(postcondition.evaluate(result));
        }
    }

    private void handleContractEvaluations(ContractEvaluation evaluation) {
        if (!evaluation.successful()) {
            throw new RuntimeException(evaluation.getError());
        }
    }

    private ContractBundle getInvarianceBundle(Method method) {
        String key = method.getName();
        if (contracts.containsKey(key)) {
            return contracts.get(key);
        } else {
            ContractBundle bundle = ContractBundle.createFromMethod(method);
            contracts.put(key, bundle);
            return bundle;
        }
    }

    /**
     * Handle the special case of when the method is defined in Object and has
     * not been overridden.
     * 
     * @param proxy
     *            The Proxy object.
     * @param method
     *            The method that is defined by object.
     * @param args
     *            The arguments to the method.
     * @return The result of the method call.
     */
    private Object handleTypeObject(Object proxy, Method method, Object[] args) {
        String name = method.getName();
        if (EQUALS.equals(name)) {
            return proxy == args[0];
        } else if (HASHCODE.equals(name)) {
            return System.identityHashCode(proxy);
        } else if (TOSTRING.equals(name)) {
            return proxy.getClass().getName() + "@"
                    + Integer.toHexString(System.identityHashCode(proxy))
                    + ", with Invariance Handler " + this;
        } else {
            throw new IllegalStateException(String.valueOf(method));
        }
    }
}