package com.tn.jinq;

/**
 * Marks a context that can be can be written to.
 *
 * @see com.tn.jinq.Context
 */
public interface Writable<T>
{
  /**
   * Writes the <code>value</code> data object to the <code>Context</code>.
   *
   * @param value the value.
   * @throws WriteException if an error occurs during the write.
   */
  public void write(T value) throws WriteException;
}
