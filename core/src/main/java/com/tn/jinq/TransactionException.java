package com.tn.jinq;

/**
 * The exception thrown when a <i>transaction</i> related error occurs.
 */
public class TransactionException extends Exception
{
  /**
   * Creates a new <code>TransactionException</code>.
   *
   * @param message the error message.
   */
  public TransactionException(String message)
  {
    super(message);
  }

  /**
   * Creates a new <code>TransactionException</code>.
   *
   * @param message the error message.
   * @param cause   the causing exception.
   */
  public TransactionException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
