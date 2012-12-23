
import contractor.contracts.InvariantInterface;
import contractor.contracts.NotNull;
import contractor.contracts.PostNotNull;
import contractor.contracts.postcondition.Postcondition;
import contractor.contracts.precondition.Precondition;
import contractor.enforcement.InvariantEnforcer;


public class ProxyTest
{
    public static void main(String... args) {
        MyMath m = InvariantEnforcer.enforceInvariance(new Maths());
        System.out.println(m.add1(2));
        //System.out.println(m.add1(-1));
        //System.out.println(m.add1(null));
    }
}

@InvariantInterface
interface MyMath {
    
    @Postcondition(contract=PostNotNull.class)
    public Integer add1(@Precondition(contract=NotNull.class) Integer i);
}

class Maths implements MyMath{

    @Override
    public Integer add1(Integer i)
    {
        return null;
    }
}