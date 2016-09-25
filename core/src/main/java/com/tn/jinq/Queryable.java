package com.tn.jinq;

/**
 * Marks a context that can be queried with an <code>Predicate</code>.
 *
 * @see com.tn.jinq.Context
 */
public interface Queryable<T, P>
{
  /**
   * Returns the data objects that match the <code>predicate</code>.
   *
   * @param predicate the predicate.
   *
   * @return the data objects.
   * 
   * @throws DataGetException if an error occurred running the query.
   */
  public Iterable<T> select(P predicate) throws DataGetException;
}
