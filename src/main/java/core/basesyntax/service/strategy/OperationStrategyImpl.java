package core.basesyntax.service.strategy;

import core.basesyntax.service.Operation;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    public OperationHandler get(Operation operation) {
        return operationHandlers.get(operation);
    }
}
