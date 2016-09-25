package com.tn.jinq;

import java.util.Collection;

/**
 * Marks a context that allows access to the data object it contains with a simple key.
 *
 * @see com.tn.jinq.Context
 */
public interface Keyed<K, T>
{
  /**
   * Returns the data object for the specified <code>key</code>.
   *
   * @param key the key.
   *
   * @return the data object.
   *
   * @throws DataGetException if an error occurs during the get.
   */
  public T get(K key) throws DataGetException;

  /**
   * Returns the data objects for the specified <code>keys</code>.
   *
   * @param keys the keys.
   *
   * @return the data objects.
   * 
   * @throws DataGetException if an error occurs during the get.
   */
  public Iterable<T> getAll(Collection<K> keys) throws DataGetException;
}
