package com.tn.jinq.predicate;

/**
 * The exception thrown when an error occurs evaluating an <code>Predicate</code>.
 *
 * @see Predicate
 */
public class PredicateEvaluationException extends RuntimeException
{
  /**
   * Creates a new <code>PredicateEvaluationException</code>.
   *
   * @param message the error message.
   */
  public PredicateEvaluationException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>PredicateEvaluationException</code>.
   *
   * @param message the error message.
   * @param cause   the causing exception.
   */
  public PredicateEvaluationException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
