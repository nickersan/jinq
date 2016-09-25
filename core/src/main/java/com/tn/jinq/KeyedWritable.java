package com.tn.jinq;

/**
 * Marks a context that allows data objects to be writen with a simple key.
 *
 * @see com.tn.jinq.Context
 */
public interface KeyedWritable<K, T>
{
  /**
   * Writes the <code>value</code> using the specified <code>key</code>.
   *
   * @param key   the key. 
   * @param value the value.
   *
   * @throws WriteException if an error occurs during the write.
   */
  public void write(K key, T value) throws WriteException;
}
