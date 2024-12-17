package com.example.dao;

import com.example.entities.User;
import com.example.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeAll
    void setup() {
        userDAO = new UserDAO();
    }

    @BeforeEach
    void clearDatabase() {
        try (Session session = HibernateTestUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");

        userDAO.saveUser(user);

        try (Session session = HibernateTestUtil.getSessionFactory().openSession()) {
            User retrievedUser = session.get(User.class, user.getId());
            assertNotNull(retrievedUser);
            assertEquals("John", retrievedUser.getName());
        }
    }

    @Test
    void testGetUsersByName() {
        User user1 = new User();
        user1.setName("Alice");
        user1.setEmail("alice@gmail.com");

        User user2 = new User();
        user2.setName("Alice");
        user2.setEmail("alice@yahoo.com");

        userDAO.saveUser(user1);
        userDAO.saveUser(user2);

        List<User> users = userDAO.getUsersByName("Alice");
        assertEquals(2, users.size());
    }
}
