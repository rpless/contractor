import contractor.contracts.Contracted;
import contractor.contracts.Post;
import contractor.contracts.Pre;
import contractor.impl.NotNull;

/**
 * A simple interface to demonstrate the NotNull contract being used as 
 * precondition and a postcondition.
 *
 */
@Contracted
public interface MyMath {

    @Post(NotNull.class)
    public Integer add1(@Pre(NotNull.class) Integer i);

    @Post(NotNull.class)
    public Double getPi();
}

class Maths implements MyMath {

    @Override
    public Integer add1(Integer i) {
        return i + 1;
    }

    @Override
    @Post(NotNull.class)
    public Double getPi() {
        return null;
    }

    public String toString() {
        return "Maths";
    }
}