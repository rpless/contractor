package contractor.contracts;

/**
 * 
 * @author Ryan Plessner
 *
 * @param <T>
 * @param <V>
 */
public abstract class Contract<T, V> {
    public abstract ContractEvaluation evaluate(V value); 
}