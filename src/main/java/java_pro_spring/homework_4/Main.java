package java_pro_spring.homework_4;

import java_pro_spring.homework_4.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            UserService service = ctx.getBean(UserService.class);

            service.createUser("Семен1");
            service.createUser("Jay_Z");

            //негативные проверки
//            service.createUser("");
//            service.createUser("Очень_длинное_имя_пользователя_много_много_букв");
//            service.createUser("!!!!");
//            service.createUser(null);

            System.out.println(service.getAllUsers());

            System.out.println("Поиск 'Сем': " + service.searchUsers("Ив", 10, 0));

            service.updateUsernames(Map.of(1L, "Сергей_рабочий", 2L, "Петр_домашний"));
            System.out.println(service.getAllUsers());

            service.deleteUser(1L);
            System.out.println(service.getAllUsers());
        }
    }

}
