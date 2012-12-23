package contractor.contracts;

import contractor.contracts.postcondition.PostconditionContract;

public class PostNotNull extends PostconditionContract<Object, Object>{

    @Override
    public ContractEvaluation evaluate(final Object value) {
	return new ContractEvaluation() {
	    
	    @Override
	    public boolean successful() {
		return value != null;
	    }
	    
	    @Override
	    public String getError() {
		return "Expected result to be nonnull, but the result was null.";
	    }
	};
    }

}
