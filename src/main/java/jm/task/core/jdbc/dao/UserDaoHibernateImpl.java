package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "create table if not exists user (id bigint not null auto_increment primary key, " +
                "name varchar(20) not null, lastname varchar(20) not null, age tinyint not null)";
        try (Session sess = Util.getSessionFactory().openSession()) {
            sess.beginTransaction();
            sess.createSQLQuery(sql).executeUpdate();
            sess.getTransaction().commit();
            System.out.println("Table created.");
        } catch (HibernateException e) {
            System.err.println("Table creation failed.");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "drop table if exists user";
        try (Session sess = Util.getSessionFactory().openSession()) {
            sess.beginTransaction();
            sess.createSQLQuery(sql).executeUpdate();
            sess.getTransaction().commit();
            System.out.println("Table deleted.");
        } catch (HibernateException e) {
            System.err.println("Table deletion failed.");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session sess = Util.getSessionFactory().openSession()) {
            sess.beginTransaction();
            sess.save(new User(name, lastName, age));
            sess.getTransaction().commit();
            System.out.println("User with name - " + name + " added to database.");
        } catch (HibernateException e) {
            System.err.println("User addition failed.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session sess = Util.getSessionFactory().openSession()) {
            sess.beginTransaction();
            sess.delete(new User() {{setId(id);}} );
            sess.getTransaction().commit();
            System.out.println("User removed from table.");
        } catch (Throwable e) {
            System.err.println("User removal failed.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "from User";
        List<User> users = new ArrayList<>();
        try (Session sess = Util.getSessionFactory().openSession()) {
            sess.beginTransaction();
            users = sess.createQuery(hql).list();
            sess.getTransaction().commit();
            System.out.println("All users perceived.");
        } catch (HibernateException e) {
            System.err.println("Users perception failed.");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String hql = "delete from User";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table cleaned.");
        } catch (HibernateException e) {
            System.err.println("Users cleaning failed");
        }
    }
}
