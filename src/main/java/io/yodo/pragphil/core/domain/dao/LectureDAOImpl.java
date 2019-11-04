package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Page<Lecture> findOnPage(int pageNo, int numRecords) {
        Session sess = sessionFactory.getCurrentSession();

        List<Lecture> lectures = sess.createQuery("from Lecture l order by id asc", Lecture.class)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .getResultList();

        long total = sess.createQuery("select count(l) from Lecture l", Long.class)
                .getSingleResult();

        return new Page<>(lectures, total, pageNo, numRecords);
    }

    @Override
    public Page<Lecture> findByLecturer(int userId, int pageNo, int numRecords) {
        Session sess = sessionFactory.getCurrentSession();

        String q1 = "select l from Lecture l join l.lecturer u where u.id = :id";
        List<Lecture> lectures = sess.createQuery(q1, Lecture.class)
                .setParameter("id", userId)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .getResultList();

        String q2 = "select count(l) from Lecture l join l.lecturer u where u.id = :id";
        long total = sess.createQuery(q2, Long.class)
                .setParameter("id", userId)
                .getSingleResult();

        return new Page<>(lectures, total, pageNo, numRecords);
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
    public Page<Lecture> findAttendedLectures(int studentId, int pageNo, int numRecords) {
        Session sess = sessionFactory.getCurrentSession();

        String q = "select l from Lecture l join l.students s where s.id = :id";
        List<Lecture> lectures = sess.createQuery(q, Lecture.class)
                .setParameter("id", studentId)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .getResultList();

        String q2 = "select count(l) from Lecture l join l.students s where s.id = :id";
        long total = sess.createQuery(q2, Long.class)
                .setParameter("id", studentId)
                .getSingleResult();

        return new Page<>(lectures, total, pageNo, numRecords);
    }

    @Override
    public Page<Lecture> findEligibleLectures(int studentId, int pageNo, int numRecords) {
        Session sess = sessionFactory.getCurrentSession();

        // all lectures the user is already enrolled in
        String q1a = "select l.id from Lecture l join l.students s where :id = s.id";
        List<Integer> asStudent = sess.createQuery(q1a, Integer.class)
                .setParameter("id", studentId)
                .getResultList();

        // all lectures the user is lecturer of
        String q1b = "select l.id from Lecture l join l.lecturer u where u.id = :id";
        List<Integer> asLecturer = sess.createQuery(q1b, Integer.class)
                .setParameter("id", studentId)
                .getResultList();

        // combine ids, avoid duplicates
        Set<Integer> ids = new HashSet<>();
        ids.addAll(asStudent);
        ids.addAll(asLecturer);

        // records for page
        String q2 = "select l from Lecture l where l.id not in :ids";
        List<Lecture> lectures = sess.createQuery(q2, Lecture.class)
                .setParameterList("ids", ids)
                .setFirstResult((pageNo-1) * numRecords)
                .setMaxResults(numRecords)
                .getResultList();

        // total number of records
        String q3 = "select count(l) from Lecture l where l.id not in :ids";
        long total = sess.createQuery(q3, Long.class)
                .setParameterList("ids", ids)
                .getSingleResult();

        return new Page<>(lectures, total, pageNo, numRecords);
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
}
