package com.tn.jinq.predicate;

/**
 * The interface to an <code>Object</code> that can be used to interpret <code>Predicate</code>s into a <code>T</code> that performs the
 * predicate logic.
 */
public interface PredicateInterpreter<T>
{
  /**
   * Interprets the <code>predicate</code>.
   *
   * @param predicate the <code>predicate</code>.
   *
   * @return a <code>T</code> that performs the predicate logic.
   *
   * @throws PredicateEvaluationException if an error occurs during the interpretation.
   */
  public T interpret(Predicate predicate) throws PredicateEvaluationException;
}
