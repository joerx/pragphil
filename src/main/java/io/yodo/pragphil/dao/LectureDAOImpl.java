package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class LectureDAOImpl implements LectureDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public LectureDAOImpl(SessionFactory sessionFactory) {
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
        return sess.createQuery("select l from Lecture l left join fetch l.lecturer where l.id = :id", Lecture.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Lecture> findAttendedLectures(int studentId) {
        Session sess = sessionFactory.getCurrentSession();
        String q = "select l from Lecture l join l.students s where s.id = :id";
        return sess.createQuery(q, Lecture.class)
                .setParameter("id", studentId)
                .getResultList();
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

    @Override
    public List<User> findStudents(int lectureId) {
        Session sess = sessionFactory.getCurrentSession();
        String q = "select u from User u join u.attendedLectures l where l.id = :id";
        return sess.createQuery(q, User.class)
                .setParameter("id", lectureId)
                .getResultList();
    }

    @Override
    public List<Lecture> findByLecturer(int userId) {
        Session sess = sessionFactory.getCurrentSession();
        String q = "select l from Lecture l join l.lecturer u where u.id = :id";
        return sess.createQuery(q, Lecture.class)
                .setParameter("id", userId)
                .getResultList();
    }
}
