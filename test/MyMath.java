import contractor.contracts.Contracted;
import contractor.contracts.Post;
import contractor.contracts.Pre;
import contractor.impl.NotNull;


@Contracted
public interface MyMath {

    @Post(NotNull.class)
    public Integer add1(@Pre(NotNull.class) Integer i);

    @Post(NotNull.class)
    public Integer returnNull();
}