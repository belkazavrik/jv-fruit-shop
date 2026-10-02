package core.basesyntax.service.strategy;

import core.basesyntax.service.Operation;

public interface OperationStrategy {
    public OperationHandler get(Operation operation);
}
