package com.tn.jinq;

/**
 * The exception thrown when an error occurs getting data.
 */
public class DataGetException extends Exception
{
  /**
   * Creates a new <code>DataGetException</code>.
   *
   * @param message the error message.
   */
  public DataGetException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>DataGetException</code>.
   *
   * @param message the error message.
   * @param cause   the causing exception.
   */
  public DataGetException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
