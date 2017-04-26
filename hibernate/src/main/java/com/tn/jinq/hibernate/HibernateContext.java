package com.tn.jinq.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Context;
import com.tn.jinq.Queryable;
import com.tn.jinq.Writable;
import com.tn.jinq.Transactional;
import com.tn.jinq.DataGetException;
import com.tn.jinq.WriteException;
import com.tn.jinq.hibernate.predicate.HibernatePredicateInterpreter;

/**
 * An implementation of <code>Context</code> that works with Hibernate.
 */
public class HibernateContext<T, P extends Predicate> extends Context<Criterion> implements Queryable<T, P>, Writable<T>, Transactional
{
  private SessionFactory sessionFactory;
  private Session session;
  private Class<T> targetClass;
  private Transaction transaction;

  /**
   * Creates a new <code>HibernateContext</code>.
   *
   * @param sessionFactory the session factory used to create <code>Session</code>s.
   * @param targetClass the class of objects returned.
   */
  public HibernateContext(SessionFactory sessionFactory, Class<T> targetClass)
  {
    super(new HibernatePredicateInterpreter());
    this.sessionFactory = sessionFactory;
    this.targetClass = targetClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<T> select(P predicate) throws DataGetException
  {
    Session session = openSession();
    try
    {
      //noinspection unchecked
      return session.createCriteria(targetClass).add(getPredicateInterpreter().interpret(predicate)).list();
    }
    catch (HibernateException e)
    {
      throw new DataGetException("An error occurred getting the data.", e);
    }
    finally
    {
      closeSession(session);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(T value) throws WriteException
  {
    Session session = openSession();
    try
    {
      session.save(value);
    }
    catch (HibernateException e)
    {
      throw new WriteException("An error occurred writing the data.", e);
    }
    finally
    {
      closeSession(session);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void beginTransaction() throws TransactionException
  {
    session = sessionFactory.openSession();
    transaction = session.beginTransaction();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commitTransaction() throws TransactionException
  {
    if (transaction != null)
    {
      transaction.commit();
      session.close();
      transaction = null;
      session = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rollbackTransaction() throws TransactionException
  {
    if (transaction != null)
    {
      transaction.rollback();
      session.close();
      transaction = null;
      session = null;
    }
  }

  /**
   * Opens a new <code>Session</code>.
   */
  protected Session openSession()
  {
    if (session != null)
    {
      return session;
    }
    else
    {
      return sessionFactory.openSession();
    }
  }

  /**
   * Closes the <code>session</code>.
   */
  protected void closeSession(Session session)
  {
    if (!session.equals(this.session))
    {
      session.close();
    }
  }
}
