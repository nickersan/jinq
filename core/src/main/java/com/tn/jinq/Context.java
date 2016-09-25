package com.tn.jinq;

import com.tn.jinq.predicate.PredicateInterpreter;

/**
 * A context represents a source of data objects, such as Coherence or Hibernate.  This class is the base class that contexts can specialize
 * if they support <code>Queryable</code>.
 *
 * @see Queryable
 */
public class Context<E>
{
  private PredicateInterpreter<E> predicateInterpreter;

  /**
   * Creates a new <code>Context</code>.
   *
   * @param predicateInterpreter the <code>PredicateInterpreter</code> used to interpret <code>Predicate</code>s so they can be run within a
   *                             particular context.
   */
  public Context(PredicateInterpreter<E> predicateInterpreter)
  {
    this.predicateInterpreter = predicateInterpreter;
  }

  /**
   * Sets the predicate interpreter.
   *
   * @param predicateInterpreter the <code>PredicateInterpreter</code> used to interpret <code>Predicate</code>s so they can be run within a
   *                             particular context.
   */
  public void setPredicateInterpreter(PredicateInterpreter<E> predicateInterpreter)
  {
    this.predicateInterpreter = predicateInterpreter;
  }

  /**
   * Returns the <code>PredicateInterpreter</code> used to interpret <code>Predicate</code>s so they can be run within a particular context.
   */
  protected PredicateInterpreter<E> getPredicateInterpreter()
  {
    return predicateInterpreter;
  }
}
