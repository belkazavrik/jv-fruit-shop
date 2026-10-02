package core.basesyntax.service.strategy;

import core.basesyntax.model.Fruit;

public interface OperationHandler {
    void apply(Fruit fruit, int quantity);
}
