package java_pro_spring.homework_6.bootstrap;

import jakarta.transaction.Transactional;
import java_pro_spring.homework_6.domain.Note;
import java_pro_spring.homework_6.domain.Product;
import java_pro_spring.homework_6.domain.ProductType;
import java_pro_spring.homework_6.domain.User;
import java_pro_spring.homework_6.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java_pro_spring.homework_6.util.DataGenerator.generateAccountNumber;
import static java_pro_spring.homework_6.util.DataGenerator.generateName;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository users;

    public DataLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    @Transactional
    public void run(String... args) {

        User user = new User();
        String name = generateName();
        user.setUsername(name);

        Note note1 = new Note();
        note1.setText("Первая заметка");
        user.addNote(note1);

        Note note2 = new Note();
        note2.setText("Вторая заметка");
        user.addNote(note2);

        Product product1 = new Product();
        product1.setAccountNumber(generateAccountNumber());
        product1.setBalance(new BigDecimal("1000.01"));
        product1.setProductType(ProductType.ACCOUNT);
        user.addProduct(product1);

        Product product2 = new Product();
        product2.setAccountNumber(generateAccountNumber());
        product2.setBalance(new BigDecimal("100.01"));
        product2.setProductType(ProductType.CARD);
        user.addProduct(product2);

        users.save(user);

        users.findByUsername(name).ifPresent(loaded -> {
            System.out.println("Loaded user: " + loaded);
            System.out.println("Notes count: " + loaded.getNotes().size());
            System.out.println("Products count: " + loaded.getProducts().size());
        });
    }

}
