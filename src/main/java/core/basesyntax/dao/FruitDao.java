package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Optional;

public interface FruitDao {

    void addFruit(Fruit fruit);

    Optional<Fruit> getByName(String name);
}
