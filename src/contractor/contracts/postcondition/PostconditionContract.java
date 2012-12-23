package contractor.contracts.postcondition;

import contractor.contracts.ContractEvaluation;

public abstract class PostconditionContract<T, V>
{
    public abstract ContractEvaluation<T> evaluate(V value); 
}