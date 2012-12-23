package contractor.contracts.precondition;

import contractor.contracts.ContractEvaluation;

public abstract class PreconditionContract<T, V>
{
    private T current;
    
    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public abstract ContractEvaluation evaluate(V value); 
}