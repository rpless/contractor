package contractor.contracts;

public abstract class Contract<T, V> {
    public abstract ContractEvaluation evaluate(V value); 
}