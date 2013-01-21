package contractor.impl;

import contractor.contracts.Contract;

/**
 * The {@code NotNull} contract specifies that the value put into its 
 * evaluate method is not null.
 * @author Ryan Plessner
 *
 */
public class NotNull extends Contract<Object> {

    @Override
    public boolean evaluate(Object value) {
        return value != null;
    }

    @Override
    public String getError(Object _) {
        return "Expected a nonnull parameter.";
    }
}