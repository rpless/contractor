package contractor.contracts.precondition;

import contractor.contracts.ContractEvaluation;

public abstract class PreconditionContract<T, V>
{
    private T current;
    
    public void setCurrent(T type) {
        this.current = type;
    }
    
    public T getCurrent() {
        return current;
    }
    
    public abstract ContractEvaluation evaluate(V value); 
}