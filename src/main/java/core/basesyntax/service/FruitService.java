package core.basesyntax.service;

import java.util.List;

public interface FruitService {

    List<String> readFile(String path);

    String[] parseLine(String line);

    void setValues(String[] parts);

    void process(String path);
}
