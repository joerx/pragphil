package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RolesDAOImpl implements RolesDAO {

    private final SessionFactory sessionFactory;

    public RolesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role getById(int roleId) {
        Session sess = sessionFactory.getCurrentSession();
        return sess.get(Role.class, roleId);
    }

    @Override
    public void create(Role r) {
        Session sess = sessionFactory.getCurrentSession();
        sess.save(r);
    }

    @Override
    public void update(Role r) {
        Session s = sessionFactory.getCurrentSession();
        s.update(r);
    }

    @Override
    public void delete(Role r) {
        Session s = sessionFactory.getCurrentSession();
        s.delete(r);
    }

    @Override
    public void deleteForUser(User user) {
        Session s = sessionFactory.getCurrentSession();
        s.createQuery("delete from Role where user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }
}
