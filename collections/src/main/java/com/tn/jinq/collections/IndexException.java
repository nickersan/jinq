package com.tn.jinq.collections;

/**
 * The exception thrown when an error occurs indexing data.
 */
public class IndexException extends RuntimeException
{
  /**
   * Creates a new <code>IndexException</code>.
   *
   * @param message the error message.
   */
  public IndexException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>IndexException</code>.
   *
   * @param message the error message.
   * @param cause   the causing exception.
   */
  public IndexException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
