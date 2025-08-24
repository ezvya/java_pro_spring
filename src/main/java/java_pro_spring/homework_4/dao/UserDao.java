package java_pro_spring.homework_4.dao;

import java_pro_spring.homework_4.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO users(username) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) user.setId(keys.getLong(1));
            }
        }
    }

    public User findById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? new User(resultSet.getLong("id"), resultSet.getString("username")) : null;
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, username FROM users")) {
            while (resultSet.next()) {
                list.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
            }
        }
        return list;
    }

    public void delete(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

//    Добавить метод в UserDao и UserService: List searchUsers(String usernamePart, int limit, int offset)
//    Реализация через SQL с LIKE, LIMIT, OFFSET
    public List<User> searchUsers(String part, int limit, int offset) throws SQLException {
        List<User> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id, username FROM users WHERE username LIKE ? LIMIT ? OFFSET ?")) {
            preparedStatement.setString(1, "%" + part + "%");
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
                }
            }
        }
        return result;
    }

//    Добавить метод:
//    void updateUsernames(Map idToUsernameMap)
//    Обновления должны выполняться в одной транзакции
    public void updateUsernames(Map<Long, String> updates) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?")) {
                for (Map.Entry<Long, String> entry : updates.entrySet()) {
                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setLong(2, entry.getKey());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            connection.commit();
        }
    }
}
