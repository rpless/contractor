import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import contractor.enforcement.ContractEnforcer;

/**
 * A simple test that shows how the NotNull class works.
 */
public class NotNullTest {

    private static MyMath math;
    
    @BeforeClass
    public static void setup() {
        math = ContractEnforcer.enforceContract(new Maths());
    }
    
    @Test
    public void testStuff() {
        assertThat(math.add1(2), is(3));
        assertThat(math.add1(-1), is(0));
    }
    
    @Test(expected=RuntimeException.class)
    public void testPrecondition() {
        math.add1(null);
    }
    
    @Test(expected=RuntimeException.class)
    public void testPostcondition() {
        math.getPi();
    }
}