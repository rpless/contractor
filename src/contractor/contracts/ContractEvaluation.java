package contractor.contracts;

/**
 * A {@code ContractEvaluation} is the result of a contract being checked.
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