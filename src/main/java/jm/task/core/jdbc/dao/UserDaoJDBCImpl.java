package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "create table if not exists user (id bigint not null auto_increment primary key, " +
                "name varchar(20) not null, lastname varchar(20) not null, age tinyint not null)";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Table created.");
        } catch (SQLException e) {
            System.err.println("Table creation failed.");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "drop table if exists user";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Table deleted.");
        } catch (SQLException e) {
            System.err.println("Table deletion failed.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into user (name, lastname, age) values (?, ?, ?)";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User with name - " + name + " added to database.");
        } catch (SQLException e) {
            System.err.println("User addition failed.");
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete from user where id = ?";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User removed from table.");
        } catch (SQLException e) {
            System.err.println("User removal failed.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select name, lastname, age from user";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(new User(
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getByte("age")));
            }
            System.out.println("All users perceived.");
        } catch (SQLException e) {
            System.err.println("Users perception failed.");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "delete from user";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Table cleaned.");
        } catch (SQLException e) {
            System.out.println("Table cleaning failed.");
        }
    }
}
