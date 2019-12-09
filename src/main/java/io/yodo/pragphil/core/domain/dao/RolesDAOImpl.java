package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolesDAOImpl implements RolesDAO {

    private final SessionFactory sessionFactory;

    public RolesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findById(int roleId) {
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
    public List<Role> getAllRoles() {
        Session s = sessionFactory.getCurrentSession();
        return s.createQuery("from Role order by name asc", Role.class).getResultList();
    }
}
