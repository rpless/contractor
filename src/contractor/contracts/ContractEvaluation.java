package contractor.contracts;

public abstract class ContractEvaluation<T>
{
    public abstract boolean successful();
    
    public abstract String getError();
}