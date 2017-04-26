package com.tn.jinq.collections;

import java.util.Map;

import com.tn.jinq.predicate.Predicate;

/**
 * A specialization of <code>Context</code> that works with any <code>Map</code> allowing its keys to be accessed as a
 * <code>Queryable</code>.
 */
public class MapKeyContext<T, P extends Predicate> extends IterableContext<T, P>
{
  /**
   * Creates a new <code>MapKeyContext</code>.
   *
   * @param map the <code>Map</code> to use within this <code>Context</code>.
   */
  public MapKeyContext(Map<T, ?> map)
  {
    super(map.keySet());
  }
}
