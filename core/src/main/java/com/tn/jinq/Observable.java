package com.tn.jinq;

/**
 * Marks a context that publishes notifications when data objects change.
 *
 * @see com.tn.jinq.Context
 */
public interface Observable<T>
{
  /**
   * Adds the <code>observer</code> so that it is fired when changes occur to the data objects.
   *
   * @param observer the observer.
   */
  public void subscribe(Observer<T> observer);

  /**
   * Removes the <code>observer</code>.
   *
   * @param observer the observer.
   */
  public void unsubscribe(Observer<T> observer);
}
