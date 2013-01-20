import contractor.contracts.Contracted;
import contractor.contracts.Post;
import contractor.contracts.Pre;
import contractor.enforcement.ContractEnforcer;
import contractor.impl.NotNull;

public class ProxyTest {
    public static void main(String... args) {
        MyMath m = ContractEnforcer.enforceContract(new Maths());

        System.out.println(m.add1(2));
        System.out.println(m.add1(-1));
        
        try {
            System.out.println(m.add1(null));
        } catch(RuntimeException e) {
            System.out.println("Addition failed because argument was null.");  
        }
        
        try {
            System.out.println(m.returnNull(1));
        } catch(RuntimeException e) {
            System.out.println("Return Null failed because it returned null.");  
        }
    }
}

@Contracted
interface MyMath {

    @Post(NotNull.class)
    public Integer add1(@Pre(NotNull.class) Integer i);
    
    @Post(NotNull.class)
    public Integer returnNull(@Pre(NotNull.class) Integer i);
}

class Maths implements MyMath {

    @Override
    public Integer add1(Integer i) {
        return i + 1;
    }
    
    @Override
    @Post(NotNull.class)
    public Integer returnNull(@Pre(NotNull.class) Integer i) {
        return null;
    }

    public String toString() {
        return "Maths";
    }
}