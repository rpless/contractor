package contractor.enforcement;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import contractor.contracts.Contracted;

/**
 * The {@code ContractEnforcer} is used to enforce an object's contracts.
 * <p>
 * It provides the enforceInvariance method, which can used to enforce an
 * object's {@code Contracted} interfaces.
 * 
 * @author Ryan Plessner
 * 
 */
@SuppressWarnings("unchecked")
public class ContractEnforcer {
    /**
     * @param t The object whose contracts will now be enforced.
     * @return Returns the object that was inputed into the function, but now
     *         the object's {@code Contracted} interfaces will be enforced.
     */
    public static <T> T enforceContract(T t) {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                getContractedInterfaces(t.getClass()),
                new ContractInvocationHandler(t));
    }

    /**
     * @param clazz The class of the object use {@code Contracted} interfaces will
     *              be extracted.
     * @return Get all of the Interfaces with the {@code Contracted} annotation.
     */
    private static Class<?>[] getContractedInterfaces(Class<?> clazz) {
        List<Class<?>> clazzes = new ArrayList<>();
        for (Class<?> c : clazz.getInterfaces()) {
            if (c.isAnnotationPresent(Contracted.class)) {
                clazzes.add(c);
            }
        }
        return clazzes.toArray(new Class<?>[0]);
    }
}