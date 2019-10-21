package io.yodo.pragphil.core.dao;

import io.yodo.pragphil.core.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAll() {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery( "from User order by id asc", User.class).getResultList();
    }

    @Override
    public User findById(int id) {
        Session s = sessionFactory.getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public User findById(int id, String role) {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery(
        "select u from User u left join u.roles r where u.id = :id and r.name = :role",
                    User.class)
                .setParameter("id", id)
                .setParameter("role", role)
                .getSingleResult();
    }

    @Override
    public User findByToken(Object token) {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery(
        "select u from User u where u.apiToken = :token and u.enabled = true",
                    User.class)
                .setParameter("token", token)
                .getSingleResult();
    }

    @Override
    public User findByUsername(String username) {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User findByUsername(String username, String role) {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery(
        "select u from User u left join u.roles r where u.username = :username and r.name = :role",
                    User.class)
                .setParameter("username", username)
                .setParameter("role", role)
                .getSingleResult();
    }

    @Override
    public List<User> findByRole(String roleName) {
        Session s = sessionFactory.getCurrentSession();
        String query = "select u from User u join u.roles r where r.name = :rolename";
        return s.createQuery(query, User.class)
                .setParameter("rolename", roleName)
                .getResultList();
    }

    @Override
    public void create(User user) {
        Session s = sessionFactory.getCurrentSession();
        s.save(user);
    }

    @Override
    public void update(User user) {
        Session s = sessionFactory.getCurrentSession();
        s.update(user);
    }

    @Override
    public void delete(User user) {
        Session s = sessionFactory.getCurrentSession();
        s.delete(user);
    }
}
