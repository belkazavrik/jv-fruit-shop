package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FruitServiceImpl implements FruitService {

    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationStrategy operationStrategy;

    public FruitServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public List<String> readFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("file not found ", e);
        }
    }

    @Override
    public String[] parseLine(String line) {
        return line.split(",");
    }

    @Override
    public void setValues(String[] parts) {
        Operation operationCode = Operation.getOperationByCode(parts[0].trim());
        String name = parts[1].trim();
        int quantity = Integer.parseInt(parts[2].trim());

        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        Optional<Fruit> existingFruit = fruitDao.getByName(name);

        if ((operationCode == Operation.BALANCE || operationCode == Operation.SUPPLY)
                && existingFruit.isEmpty()) {
            fruitDao.addFruit(new Fruit(name, quantity));
            return;
        }

        if ((operationCode == Operation.RETURN || operationCode == Operation.PURCHASE)
                && existingFruit.isEmpty()) {
            throw new RuntimeException("The store doesn't have a " + name + " fruit");
        }
        Fruit fruit = existingFruit.get();
        OperationHandler handler = operationStrategy.get(operationCode);
        handler.apply(fruit, quantity);
    }

    @Override
    public void process(String path) {
        List<String> lines = readFile(path);

        for (String line: lines) {
            if (line.startsWith("type")) {
                continue;
            }
            String[] parts = parseLine(line);
            setValues(parts);
        }
    }
}
