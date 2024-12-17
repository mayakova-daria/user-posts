package com.example.dao;

import com.example.entities.User;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDAO {
    public void saveUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public User getUserById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    public List<User> getUsersByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE name = :name", User.class)
                    .setParameter("name", name)
                    .list();
        }
    }
}
