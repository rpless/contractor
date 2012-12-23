package contractor.contracts;

import contractor.contracts.postcondition.PostconditionContract;

public class PostNotNull extends PostconditionContract<Object, Object>{

    @Override
    public ContractEvaluation<Object> evaluate(final Object value) {
	return new ContractEvaluation<Object>() {
	    
	    @Override
	    public boolean successful() {
		return value != null;
	    }
	    
	    @Override
	    public String getError() {
		return "Foo";
	    }
	};
    }

}
