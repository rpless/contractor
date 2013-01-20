package contractor.contracts;

/**
 * A {@code ContractEvaluation} is the result of a contract being applied to the
 * arguments or result of a method.
 * 
 * @author Ryan Plessner
 * 
 */
public abstract class ContractEvaluation {
    
    /**
     * @return Did the value conform with the contract?
     */
    public abstract boolean successful();

    /**
     * @return The error message of a failed contract. 
     *         Precondition: a call to this.successful() == false;
     */
    public abstract String getError();
}