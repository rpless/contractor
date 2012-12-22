package contractor.enforcement;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import contractor.contracts.InvariantInterface;

public class InvariantEnforcer
{
    @SuppressWarnings("unchecked")
    public static <T> T enforceInvariance(T t) 
    {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), getInvariantInterfaces(t.getClass()), 
                                          new InvarianceInvocationHandler(t));
    }
    
    private static Class<?>[] getInvariantInterfaces(Class<?> clazz) {
        List<Class<?>> clazzes = new ArrayList<>();
        for(Class<?> c : clazz.getInterfaces()) {
            if(c.isAnnotationPresent(InvariantInterface.class)) {
                clazzes.add(c);
            }
        }
        return clazzes.toArray(new Class<?>[0]);
    }
}