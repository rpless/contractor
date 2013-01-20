import contractor.contracts.Post;
import contractor.impl.NotNull;

public class Maths implements MyMath {

    @Override
    public Integer add1(Integer i) {
        return i + 1;
    }

    @Override
    @Post(NotNull.class)
    public Integer returnNull() {
        return null;
    }

    public String toString() {
        return "Maths";
    }
}