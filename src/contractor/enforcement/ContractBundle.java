package contractor.enforcement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import contractor.contracts.Contract;
import contractor.contracts.Post;
import contractor.contracts.Pre;

/**
 * An {@code ContractBundle} is a bundle of contracts for a given method.
 * 
 * @author Ryan Plessner
 * TODO
 */
@SuppressWarnings("rawtypes")
class ContractBundle {
    private final String methodName;
    private final List<List<Contract>> preconditions;
    private final List<Contract> postconditions;

    static ContractBundle createFromMethod(Method method) {
        return new ContractBundle(method.getName(), getPreconditions(method),
                getPostconditions(method));
    }

    private static List<List<Contract>> getPreconditions(Method method) {
        List<List<Contract>> preconditions = new LinkedList<>();
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            List<Contract> argConditions = new LinkedList<>();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Pre) {
                    try {
                        Pre precondition = (Pre) annotation;
                        argConditions.add(precondition.value().newInstance());
                    } catch(InstantiationException | IllegalAccessException e) {
                        StringBuilder error = new StringBuilder();
                        error.append("Unable to create Precondition for ").
                              append(annotation.getClass().getName());
                        throw new RuntimeException(error.toString(), e);
                    }
                }
            }
            preconditions.add(argConditions);
        }
        return preconditions;
    }

    private static List<Contract> getPostconditions(Method method) {
        List<Contract> postconditions = new LinkedList<>();
        for (Annotation anno : method.getAnnotations()) {
            if (anno instanceof Post) {
                try {
                    postconditions.add(((Post) anno).value().newInstance());
                } catch (InstantiationException e) {
                    throw new RuntimeException(
                            "Unable to instantiate a Precondition contract "
                                    + anno.getClass().getName() + ".", e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(
                            "Unable to access a Precondition's constructor "
                                    + anno.getClass().getName() + ".", e);
                }

            }
        }
        return postconditions;
    }

    private ContractBundle(String methodName, List<List<Contract>> preconditions, List<Contract> postconditions) {
        this.methodName = methodName;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
    }

    List<List<Contract>> getPreconditions() {
        return preconditions;
    }

    List<Contract> getPostconditions() {
        return postconditions;
    }

    public String toString() {
        return "Contracts for " + methodName;
    }
}