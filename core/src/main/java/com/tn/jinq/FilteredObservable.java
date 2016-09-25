package com.tn.jinq;

/**
 * Marks a context that publishes notifications when data object that match a given <code>Predicate</code> change.
 *
 * @see com.tn.jinq.Context
 */
public interface FilteredObservable<T, P>
{
  /**
   * Adds the <code>observer</code> so that it is fired when changes occur to data objects matching the <code>predicate</code>.
   *
   * @param observer  the observer.
   * @param predicate the predicate.
   */
  public void subscribe(Observer<T> observer, P predicate);

  /**
   * Removes the <code>observer</code>.
   *
   * @param observer the observer.
   */
  public void unsubscribe(Observer<T> observer);
}
