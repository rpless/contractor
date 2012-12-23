package contractor.contracts;

import contractor.contracts.ContractEvaluation;
import contractor.contracts.precondition.PreconditionContract;


public class NotNull extends PreconditionContract<Object, Object>
{
    @Override
    public ContractEvaluation<Object> evaluate(final Object value)
    {
        return new ContractEvaluation<Object>() {

            @Override
            public boolean successful()
            {
                return value != null;
            }

            @Override
            public String getError()
            {
                return "Expected a nonnull parameter for " + getCurrent() + ", but parameter was null.";
            }
        };
    }
}