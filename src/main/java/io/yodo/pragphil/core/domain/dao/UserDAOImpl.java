package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
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
    public Page<User> findOnPage(int pageNo, int numRecords) {
        Session s = sessionFactory.getCurrentSession();

        List<User> users = s.createQuery("from User u", User.class)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .getResultList();

        long total = s.createQuery("select count(u) from User u", Long.class).getSingleResult();

        return new Page<>(users, total, pageNo, numRecords);
    }

    @Override
    public Page<User> findByRole(String rolename, int pageNo, int numRecords) {
        Session s = sessionFactory.getCurrentSession();

        String q1 = "select u from User u join u.roles r where r.name = :rolename";
        List<User> users = s.createQuery(q1, User.class)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .setParameter("rolename", rolename)
                .getResultList();

        String q2 = "select count(u) from User u join u.roles r where r.name = :rolename";
        long total = s.createQuery(q2, Long.class)
                .setParameter("rolename", rolename)
                .getSingleResult();

        return new Page<>(users, total, pageNo, numRecords);
    }

    @Override
    public List<User> findByRole(String role) {
        Session s = sessionFactory.getCurrentSession();
        return  s.createQuery("select u from User u join u.roles r where r.name = :role", User.class)
                .setParameter("role", role)
                .getResultList();
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
