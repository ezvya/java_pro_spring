package java_pro_spring.homework_4.service;

import java_pro_spring.homework_4.exceptions.UserValidationException;
import java_pro_spring.homework_4.dao.UserDao;
import java_pro_spring.homework_4.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java_pro_spring.homework_4.service.UserValidator.validate;

public class UserService {
    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void createUser(String username) {
        validate(username);
        try {
            User user = new User(null, username);
            dao.create(user);
            System.out.println("Создан пользователь: " + user);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при создании", e);
        }
    }

    public User getUser(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при получении", e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при получении списка", e);
        }
    }

    public void deleteUser(Long id) {
        try {
            dao.delete(id);
            System.out.println("Удалён пользователь с id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при удалении", e);
        }
    }

    public List<User> searchUsers(String part, int limit, int offset) {
        try {
            return dao.searchUsers(part, limit, offset);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при поиске", e);
        }
    }

    public void updateUsernames(Map<Long, String> updates) {
        for (String name : updates.values()) {
            validate(name);
        }
        try {
            dao.updateUsernames(updates);
            System.out.println("Массовое обновление: " + updates);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при обновлении", e);
        }
    }
}
