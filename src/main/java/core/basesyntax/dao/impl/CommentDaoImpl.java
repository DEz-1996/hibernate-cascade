package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private static final String CANT_CREATE_MSG = "Can't create comment entity: ";
    private static final String CANT_GET_BY_ID_MSG = "Can't get comment by id: ";
    private static final String CANT_GET_ALL_MSG = "Can't get all comments";
    private static final String CANT_REMOVE_MSG = "Can't remove comment entity: ";
    private static final String SELECT_ALL_QUERY = "SELECT a FROM Comment a";

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANT_CREATE_MSG + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_ALL_MSG, e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANT_REMOVE_MSG + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
