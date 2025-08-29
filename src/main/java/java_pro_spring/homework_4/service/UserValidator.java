package java_pro_spring.homework_4.service;

import java_pro_spring.homework_4.exceptions.UserValidationException;

public class UserValidator {

//    Валидация username
//    При создании и обновлении пользователя проверять:
//    Не пустой
//    Длина от 3 до 30 символов
//    Только буквы, цифры и подчёркивания (регулярка: ^[a-zA-Z0-9_]+$)
//    При нарушении — выбрасывать ValidationException

    public static void validate(String username) {
        if (username == null || username.isBlank())
            throw new UserValidationException("Username пустой");
        if (username.length() < 3 || username.length() > 30)
            throw new UserValidationException("Длина username должна быть от 3 до 30");
        if (!username.matches("^[a-zA-Zа-яА-я0-9_]+$"))
            throw new UserValidationException("Username может содержать только буквы, цифры и _");
    }
}
