package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.User;
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
    public User findByUsername(String username) {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
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
