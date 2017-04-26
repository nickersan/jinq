package com.tn.jinq.collections;

import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Keyed;
import com.tn.jinq.Queryable;
import com.tn.jinq.DataGetException;

/**
 * A specialization of <code>Context</code> that works with any <code>Map</code> allowing its value to be accessed as a
 * <code>Keyed</code> or <code>Queryable</code>.
 */
public class MapValueContext<K, T, P extends Predicate> extends IterableContext<T, P> implements Keyed<K, T>, Queryable<T, P>
{
  private Map<K, T> map;

  /**
   * Creates a new <code>MapValueContext</code>.
   *
   * @param map the <code>Map</code> to use within this <code>Context</code>.
   */
  public MapValueContext(Map<K, T> map)
  {
    super(map.values());
    this.map = map;
  }

  /**
   * {@inheritDoc}
   */
  public T get(K key) throws DataGetException
  {
    return map.get(key);
  }

  /**
   * {@inheritDoc}
   */
  public Iterable<T> getAll(Collection<K> keys) throws DataGetException
  {
    Collection<T> values = new ArrayList<T>();

    for (K key : keys)
    {
      T object = map.get(key);
      if (object != null)
      {
        values.add(object);
      }
    }

    return values;
  }
}
