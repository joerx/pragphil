package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Lecture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LecturesDAOImpl implements LecturesDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public LecturesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Lecture> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        return sess.createQuery("from Lecture order by id asc", Lecture.class).getResultList();
    }

    @Override
    public Lecture findById(int id) {
        Session sess = sessionFactory.getCurrentSession();
        return sess.get(Lecture.class, id);
    }

    @Override
    public void create(Lecture l) {
        Session sess = sessionFactory.getCurrentSession();
        sess.save(l);
    }

    @Override
    public void update(Lecture l) {
        Session sess = sessionFactory.getCurrentSession();
        sess.update(l);
    }

    @Override
    public void delete(Lecture l) {
        Session sess = sessionFactory.getCurrentSession();
        sess.delete(l);
    }
}
