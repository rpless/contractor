package contractor.contracts;

/**
 * A {@code Contract} is agreement that method must adhere to.
 * <p>
 * If the method has a contract and it is being enforced, then a violation
 * will result in a runtime error.
 * @author Ryan Plessner
 * 
 * @param <V> The value that is being passed in to evaluate.
 */
public abstract class Contract<V> {
    
    /**
     * @param value The value to chek the contract again.
     * @return Returns the evaluation of a contract.
     */
    public abstract ContractEvaluation evaluate(V value);
}