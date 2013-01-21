package contractor.contracts;

/**
 * A {@code Contract} is an agreement that a method must adhere to.
 * <p>
 * If the method has a contract and it is being enforced, then a violation
 * of the contract will result in a runtime error.
 * @author Ryan Plessner
 * 
 * @param <V> The value that is being passed in to evaluate.
 */
public abstract class Contract<V> {
    
    /**
     * @param value The value to check the contract again.
     * @return Returns true if the contract is satisfied.
     */
    public abstract boolean evaluate(V value);
    
    /**
     * @param value A value that violates the contract. 
     *              That is a call to evaluate on it returns false.
     * @return Returns a string that describes the contract violation.
     */
    public abstract String getError(V value);
}