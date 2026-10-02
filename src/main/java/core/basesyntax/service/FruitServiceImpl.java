package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FruitServiceImpl implements FruitService {

    private final FruitDao fruitDao = new FruitDaoImpl();

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

        Optional<Fruit> existingFruit = fruitDao.getByName(name);

        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        switch (operationCode) {
            case BALANCE:
            case SUPPLY:
                if (existingFruit.isPresent()) {
                    Fruit fruit = existingFruit.get();
                    fruit.setQuantity(fruit.getQuantity() + quantity);
                } else {
                    fruitDao.addFruit(new Fruit(name, quantity));
                }
                break;
            case RETURN:
                if (existingFruit.isPresent()) {
                    Fruit fruit = existingFruit.get();
                    fruit.setQuantity(fruit.getQuantity() + quantity);
                } else {
                    throw new RuntimeException("Cannot return a "
                            + name + " because it is not in the list");
                }
                break;
            case PURCHASE:
                if (existingFruit.isPresent()) {
                    Fruit fruit = existingFruit.get();
                    if (fruit.getQuantity() >= quantity) {
                        fruit.setQuantity(fruit.getQuantity() - quantity);
                    } else {
                        throw new RuntimeException("The quantity in "
                                + "the store cannot be negative");
                    }
                } else {
                    throw new RuntimeException("The store doesn`t have a " + name + " fruit");
                }
                break;
            default:
                throw new RuntimeException("Operation is not supported: " + operationCode);
        }
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
