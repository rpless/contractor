package contractor.enforcement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import contractor.contracts.Contract;

/**
 * The {@link ContractInvocationHandler} acts as a listener that can be
 * created for objects with Contracts.
 * <p>
 * The {@code ContractInvocationHandler} is a dynamic proxy receives method calls
 * from an object with that implements interface(s) that have the 
 * {@code Contracted} annotation.
 * 
 * @author Ryan Plessner
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class ContractInvocationHandler implements InvocationHandler {

    private Object implementation; // The Object that is having its contracts enforced.
    private HashMap<String, MethodContractBundle> contracts; // All contracts associated with the object's methods

    /**
     * Create a {@code ContractInvocationHandler}.
     * @param implementation The object to received method dispatches from.
     */
    ContractInvocationHandler(Object implementation) {
        this.implementation = implementation;
        this.contracts = new HashMap<>();
    }

    /**
     * Received a dispatched method call. Check preconditions, 
     * invoke the method, and finally run the postconditions.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        MethodContractBundle bundle = getContractBundle(method);

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

    /**
     * Check all of a {@code ContractBundle} preconditions.
     * @param bundle The {@code ContractBundle} that holds preconditions.
     * @param args The arguments to the method invocation.
     */
    private void checkPreconditions(MethodContractBundle bundle, Object[] args) {
        int argIndex = 0;
        for (List<Contract> preconditions : bundle.getPreconditions()) {
            checkContracts(preconditions, args[argIndex]);
            argIndex++;
        }
    }

    /**
     * Check all postconditions on the return value
     * @param bundle The {@code ContractBundle} that holds the post condition {@code Contract}s.
     * @param result The result of the method invocation.
     */
    private void checkPostconditions(MethodContractBundle bundle, Object result) {
        checkContracts(bundle.getPostconditions(), result);
    }
    
    /**
     * Check a set of contracts on the given value.
     * @param contracts The contracts to check.
     * @param value The value to check against.
     */
    private void checkContracts(List<Contract> contracts, Object value) {
        for (Contract contract : contracts) {
            if (!contract.evaluate(value)) {
                throw new RuntimeException(contract.getError(value));
            }
        }
    }

    /**
     * @param method The method whose {@code ContractBundle} is needed.
     * @return Get the {@code ContractBundle} associated with the given method.
     */
    private MethodContractBundle getContractBundle(Method method) {
        String key = method.getName();
        if (contracts.containsKey(key)) {
            return contracts.get(key);
        } else {
            MethodContractBundle bundle = MethodContractBundle.createFromMethod(method);
            contracts.put(key, bundle);
            return bundle;
        }
    }
}