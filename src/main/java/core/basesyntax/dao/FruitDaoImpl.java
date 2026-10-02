package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Optional;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void addFruit(Fruit fruit) {
        Storage.fruits.add(fruit);
    }

    @Override
    public Optional<Fruit> getByName(String name) {
        for (Fruit fruit: Storage.fruits) {
            if (fruit.getName().equals(name)) {
                return Optional.of(fruit);
            }
        }
        return Optional.empty();
    }
}
