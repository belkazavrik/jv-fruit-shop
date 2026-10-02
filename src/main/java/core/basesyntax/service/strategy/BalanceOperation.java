package core.basesyntax.service.strategy;

import core.basesyntax.model.Fruit;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(Fruit fruit, int quantity) {
        fruit.setQuantity(fruit.getQuantity() + quantity);
    }
}
