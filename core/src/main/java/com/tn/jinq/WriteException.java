package com.tn.jinq;

/**
 * The exception thrown when a write exception occurs.
 */
public class WriteException extends Exception
{
  /**
   * Creates a new <code>WriteException</code>.
   *
   * @param message the message.
   */
  public WriteException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>WriteException</code>.
   *
   * @param message the message.
   * @param cause   the cause.
   */
  public WriteException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
