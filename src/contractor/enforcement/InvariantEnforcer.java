package contractor.enforcement;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import contractor.contracts.Invariant;

/**
 * The {@code InvariantEnforcer} is used to enforce an object's invariants.
 * <p>
 * It provides the enforceInvariance method, which can used to enforce an object's {@code Invariant} interfaces.
 * 
 * @author Ryan Plessner
 *
 */
public class InvariantEnforcer
{
    /**
     * @param t The object whose invariants will now be enforced.
     * @return Returns the object that was inputed into the function, but now the object's {@code Invariant} interfaces will be enforced.
     */
    @SuppressWarnings("unchecked")
    public static <T> T enforceInvariance(T t) 
    {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), getInvariantInterfaces(t.getClass()), 
                                          new InvarianceInvocationHandler(t));
    }
    
    /**
     * @param clazz The class of the object use {@code Invariant} interfaces will be extracted.
     * @return Get all of the Interfaces with the {@code Invariant} annotation.
     */
    private static Class<?>[] getInvariantInterfaces(Class<?> clazz) {
        List<Class<?>> clazzes = new ArrayList<>();
        for(Class<?> c : clazz.getInterfaces()) {
            if(c.isAnnotationPresent(Invariant.class)) {
                clazzes.add(c);
            }
        }
        return clazzes.toArray(new Class<?>[0]);
    }
}