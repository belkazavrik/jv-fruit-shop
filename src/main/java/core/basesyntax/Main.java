package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitServiceImpl;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        FruitService fruitService = new FruitServiceImpl();

        String path = String.valueOf(Path.of("C:\\Users\\admin\\jv-fruit-shop\\src"
                + "\\main\\java\\core\\basesyntax\\input.csv"));
        fruitService.process(path);
        System.out.println(Storage.fruits);
    }
}
