package contractor.contracts.postcondition;

import contractor.contracts.Contract;

/**
 * A {@code PostconditionContract} defines methods for all {@code Contract}s that should be evaluated as part
 * of the {@code Postcondition} annotation.
 * @author Ryan Plessner
 *
 */
public abstract class PostconditionContract<T, V> extends Contract<T, V>{}