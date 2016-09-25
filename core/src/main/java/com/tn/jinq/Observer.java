package com.tn.jinq;

/**
 * The object that provides notification information.
 */
public abstract class Observer<T>
{
  /**
   * Notifies the observer that the provider has finished sending push-based notifications.
   */
  public void onCompleted()
  {
  }

  /**
   * Notifies the observer that the provider has experienced the <code>Exception</code>.
   *
   * @param e the exception
   */
  @SuppressWarnings({"UnusedDeclaration"})
  public void onError(Exception e)
  {
  }

  /**
   * Provides the observer with the new <code>value</code>.
   *
   * @param value the value
   */
  public abstract void onNext(T value);
}
