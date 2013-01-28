package contractor.impl;

import java.util.Collection;

import contractor.contracts.Contract;

public class NonEmpty<V> extends Contract<Collection<V>> {

    @Override
    public boolean evaluate(Collection<V> value) {
        return value.isEmpty();
    }

    @Override
    public String getError(Collection<V> value) {
        return "Expected a nonempty collection, got " + value.toString();
    }
}