package contractor.impl;

import contractor.contracts.Contract;
import contractor.contracts.ContractEvaluation;

/**
 * 
 * @author Ryan Plessner
 *
 */
public class NotNull extends Contract<Object> {
    @Override
    public ContractEvaluation evaluate(final Object value) {
        return new ContractEvaluation() {

            @Override
            public boolean successful() {
                return value != null;
            }

            @Override
            public String getError() {
                return "Expected a nonnull parameter.";
            }
        };
    }
}
