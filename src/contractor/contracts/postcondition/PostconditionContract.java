package contractor.contracts.postcondition;

import contractor.contracts.ContractEvaluation;

public abstract class PostconditionContract<T, V>
{
    private T old;
    
    public void setOld(T old) {
        this.old = old;
    }
    
    public T getOld() {
        return old;
    }
    
    public abstract ContractEvaluation<T> evaluate(V value); 
}