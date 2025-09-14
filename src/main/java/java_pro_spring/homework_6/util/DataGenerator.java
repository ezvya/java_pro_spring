package java_pro_spring.homework_6.util;

import java.util.List;
import java.util.Random;


public class DataGenerator {

    private static final Random random = new Random();

    private static final List<String> names = List.of(
            "Homer", "Marge", "Maggie", "Lisa", "Bart"
    );

    public static String generateAccountNumber()  {
        return "ACC-4100000" + random.nextInt(999);
    }

    public static String generateName() {
        return names.get(random.nextInt(0, names.size() - 1)) + "_" + random.nextInt(999);
    }

}
