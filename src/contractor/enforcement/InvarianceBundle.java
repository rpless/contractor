package contractor.enforcement;

import java.util.List;

import contractor.contracts.postcondition.Postcondition;
import contractor.contracts.precondition.Precondition;

public class InvarianceBundle
{
    private final List<Precondition> preconditions;
    private final List<Postcondition> postconditions;
    
    InvarianceBundle(List<Precondition> preconditions, List<Postcondition> postconditions) {
        this.preconditions = preconditions;
        this.postconditions = postconditions;
    }

    public List<Precondition> getPreconditions()
    {
        return preconditions;
    }

    public List<Postcondition> getPostconditions()
    {
        return postconditions;
    }
}