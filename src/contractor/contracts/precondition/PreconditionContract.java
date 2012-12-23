package contractor.contracts.precondition;

import contractor.contracts.Contract;

/**
 * A {@code PreconditionContract} defines methods for all {@code Contract}s that should be evaluated as part
 * of the {@code Precondition} annotation.
 * @author Ryan Plessner
 *
 */
public abstract class PreconditionContract<T, V> extends Contract<T, V> {}