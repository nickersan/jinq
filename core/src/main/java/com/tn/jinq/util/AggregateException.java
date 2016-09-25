package com.tn.jinq.util;

/**
 * The exception thrown when an error occurs creating an <i>aggregate</i>.
 */
public class AggregateException extends Exception
{
  /**
   * Creates a new <code>AggregateException</code>.
   *
   * @param message the error message.
   */
  public AggregateException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>AggregateException</code>.
   *
   * @param message the error message.
   * @param cause   the causing exception.
   */
  public AggregateException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
