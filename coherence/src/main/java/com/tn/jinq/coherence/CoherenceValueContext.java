package com.tn.jinq.coherence;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

import com.tangosol.util.Filter;
import com.tangosol.util.MapListener;
import com.tangosol.util.MapEvent;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.ContinuousQueryCache;
import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Context;
import com.tn.jinq.Keyed;
import com.tn.jinq.Queryable;
import com.tn.jinq.KeyedWritable;
import com.tn.jinq.Observable;
import com.tn.jinq.FilteredObservable;
import com.tn.jinq.Observer;
import com.tn.jinq.DataGetException;
import com.tn.jinq.WriteException;
import com.tn.jinq.coherence.predicate.CoherencePredicateInterpreter;

/**
 * An implementation of <code>Context</code> that works with the values (as opposed to the keys) in a Coherence cache.
 */
public class CoherenceValueContext<K, T, P extends Predicate> extends Context<Filter>
  implements Keyed<K, T>, Queryable<T, P>, KeyedWritable<K, T>, Observable<T>, FilteredObservable<T, P>
{
  private NamedCache cache;
  private Map<Observer, MapListener> listeners;
  private Map<Observer, Filter> observerFilters;
  private Map<Observer, ContinuousQueryCache> observers;

  /**
   * Creates a new <code>CoherenceValueContext</code>.
   *
   * @param cache the cache to wrap.
   */
  public CoherenceValueContext(NamedCache cache)
  {
    super(new CoherencePredicateInterpreter());
    this.cache = cache;
    this.listeners = new HashMap<Observer, MapListener>();
    this.observerFilters = new HashMap<Observer, Filter>();
    this.observers = new HashMap<Observer, ContinuousQueryCache>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get(K key) throws DataGetException
  {
    //noinspection unchecked
    return (T)cache.get(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<T> getAll(Collection<K> keys) throws DataGetException
  {
    //noinspection unchecked
    return (Iterable<T>)cache.getAll(keys).values();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<T> select(final P predicate) throws DataGetException
  {
    return new Iterable<T>()
    {
      public Iterator<T> iterator()
      {
        //noinspection unchecked
        return new ValueIterator(cache.entrySet(getPredicateInterpreter().interpret(predicate)).iterator());
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(K key, T value) throws WriteException
  {
    //If there are observers then check to see if the value can be added to one of the ContinuousQueryCaches first.
    //The value only needs adding to one ContinuousQueryCaches for all MapListeners to be invoked.
    for (Observer observer : observers.keySet())
    {
      Filter observerFilter = observerFilters.get(observer);
      if (observerFilter != null && observerFilter.evaluate(value))
      {
        ContinuousQueryCache cache = observers.get(observer);
        cache.put(key, value);
        return;
      }
    }

    //noinspection unchecked
    this.cache.put(key, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void subscribe(final Observer<T> observer)
  {
    MapListener mapListener = new ObserverMapListener(observer);
    listeners.put(observer, mapListener);
    cache.addMapListener(mapListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void subscribe(final Observer<T> observer, P predicate)
  {
    if (!observers.containsKey(observer))
    {
      Filter filter = getPredicateInterpreter().interpret(predicate);
      observerFilters.put(observer, filter);

      observers.put(
        observer,
        new ContinuousQueryCache(
          cache,
          filter,
          new ObserverMapListener(observer)
        )
      );
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsubscribe(Observer<T> observer)
  {
    if (observers.containsKey(observer))
    {
      observers.remove(observer);
      observerFilters.remove(observer);
    }
    else if (listeners.containsKey(observer))
    {
      cache.removeMapListener(listeners.get(observer));
    }
  }

  /**
   * An implementation of <code>MapListener</code> that invokes an <code>Observer</code>.
   */
  private class ObserverMapListener implements MapListener
  {
    private Observer observer;

    /**
     * Creates a new <code>ObserverMapListener</code> initialized with the <code>observer</code>.
     */
    private ObserverMapListener(Observer observer)
    {
      this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entryInserted(MapEvent mapEvent)
    {
      //noinspection unchecked
      observer.onNext(mapEvent.getNewValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entryUpdated(MapEvent mapEvent)
    {
      //noinspection unchecked
      observer.onNext(mapEvent.getNewValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entryDeleted(MapEvent mapEvent)
    {
      //noinspection unchecked
      observer.onNext(mapEvent.getNewValue());
    }
  }

  /**
   * An implementation of <code>Iterator</code> that returns the values from a <code>Map.Entry</code>.
   */
  private class ValueIterator implements Iterator<T>
  {
    private Iterator<Map.Entry> entryIterator;

    /**
     * Creates a new <code>ValueIterator</code> initialized with the <code>entryIterator</code>.
     */
    private ValueIterator(Iterator<Map.Entry> entryIterator)
    {
      this.entryIterator = entryIterator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext()
    {
      return entryIterator.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T next()
    {
      //noinspection unchecked
      return (T)entryIterator.next().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove()
    {
      entryIterator.remove();
    }
  }
}
