package contractor.contracts;

/**
 * 
 * @author Ryan Plessner
 *
 * @param <T>
 */
public abstract class ContractEvaluation
{
    public abstract boolean successful();
    
    public abstract String getError();
}