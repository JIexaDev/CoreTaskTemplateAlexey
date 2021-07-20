package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("CREATE TABLE `my_schema`.`users` (\n" +
                        "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NULL,\n" +
                        "  `lastName` VARCHAR(45) NULL,\n" +
                        "  `age` TINYINT NULL,\n" +
                        "  PRIMARY KEY (`id`));")
                        .executeUpdate();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу!");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("drop table users;").executeUpdate();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.save(user);
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось сохранить юзера!");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить юзера!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                userList = session.createSQLQuery("select * from users;")
                        .addEntity(User.class)
                        .list();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось получить список юзеров!");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("truncate table users;").executeUpdate();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу!");
        }
    }
}
