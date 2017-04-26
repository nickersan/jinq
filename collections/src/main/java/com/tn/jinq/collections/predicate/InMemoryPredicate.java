package com.tn.jinq.collections.predicate;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.collections.Indexed;

/**
 * The base class that all <i>in-memory</i> predicates specialize.
 */
public abstract class InMemoryPredicate
{
  /**
   * Returns the queryable elements based on the <code>indexed</code> object based on the available indexes.
   */
  public abstract <T> Collection<T> getQueryableElements(Indexed<T> indexed);

  /**
   * Evaluates the <code>object</code> with this <code>InMemoryPredicate</code>.
   *
   * @param object the object to evaluate.
   *
   * @return <code>true</code> if the object matches this <code>InMemoryPredicate</code>; otherwise <code>false</code>.
   *
   * @throws PredicateEvaluationException if an error occurs during the evaluation.
   */
  public abstract boolean evaluate(Object object) throws PredicateEvaluationException;

  /**
   * Returns the value of the <code>object</code> to be evaluated.
   *
   * @param object the object.
   * @param getter the name of the getter to use to obtain the value.
   *
   * @return the value.
   */
  protected Object getValue(Object object, String getter)
  {
    try
    {
      return object.getClass().getMethod(getter).invoke(object);
    }
    catch (NoSuchMethodException e)
    {
      throw new PredicateEvaluationException("The getter '" + getter + "' cannot be found.", e);
    }
    catch (InvocationTargetException e)
    {
      throw new PredicateEvaluationException("An error occurred invoking the getter '" + getter + "'.", e);
    }
    catch (IllegalAccessException e)
    {
      throw new PredicateEvaluationException("The getter '" + getter + "' cannot be accessed.", e);
    }
  }
}
