package com.tn.jinq;

/**
 * Marks a context that can be supports transactions.
 *
 * @see com.tn.jinq.Context
 */
public interface Transactional
{
  /**
   * Starts a transaction.
   *
   * @throws TransactionException if an error occurs starting a transaction.
   */
  public void beginTransaction() throws TransactionException;

  /**
   * Commits a transaction.
   *
   * @throws TransactionException if an error occurs committing a transaction.
   */
  public void commitTransaction() throws TransactionException;

  /**
   * Rolls back a transaction.
   *
   * @throws TransactionException if an error occurs rolling back a transaction.
   */
  public void rollbackTransaction() throws TransactionException;
}
