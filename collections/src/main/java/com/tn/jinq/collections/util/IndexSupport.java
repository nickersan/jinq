package com.tn.jinq.collections.util;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;

import com.tn.jinq.collections.Indexed;

/**
 * A support class that is used by classes implementing <code>Indexed</code> to deal with the indexing.
 */
public class IndexSupport<T> implements Indexed<T>
{
  private Map<String, Map<Object, Set<T>>> indexes;
  private Collection<T> collection;

  /**
   * Creates a new <code>IndexSupport</code>.
   *
   * @param collection the iterable being indexed.
   */
  public IndexSupport(Collection<T> collection)
  {
    this.collection = collection;
    this.indexes    = new HashMap<String, Map<Object, Set<T>>>();
  }

  /**
   *
   */
  public Collection<T> getAllItems()
  {
    return Collections.unmodifiableCollection(collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasIndex(String getter)
  {
    return indexes.containsKey(getter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<T> getIndexedItems(String getter, Object indexKey)
  {
    return Collections.unmodifiableSet(getValues(getIndexedValues(getter), indexKey));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addIndex(String getter)
  {
    Map<Object, Set<T>> indexedValues = getIndexedValues(getter);

    for (T value : collection)
    {
      getValues(indexedValues, getIndexKey(getter, value)).add(value);
    }
  }

  /**
   * Indexes the <code>value</code>.
   */
  public void index(T element)
  {
    for (String getter : indexes.keySet())
    {
      getValues(getIndexedValues(getter), getIndexKey(getter, element)).add(element);
    }
  }

  /**
   * Indexes the all the elements in the collection.
   */
  public void indexAll(Collection<? extends T> collection)
  {
    for (T element : collection)
    {
      index(element);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeIndex(String getter)
  {
    indexes.remove(getter);
  }

  /**
   * Unindexes the <code>value</code>.
   */
  public void unindex(T element)
  {
    for (String getter : indexes.keySet())
    {
      getValues(getIndexedValues(getter), getIndexKey(getter, element)).remove(element);
    }
  }

  /**
   * Unidexes the all the elements in the collection.
   */
  public void unindexAll(Collection<? extends T> collection)
  {
    for (T element : collection)
    {
      unindex(element);
    }    
  }

  /**
   * Returns the value of the <code>object</code> to be evaluated.
   *
   * @param getter the name of the getter to use to obtain the value.   
   * @param object the object.
   *
   * @return the value.
   */
  private Object getIndexKey(String getter, Object object)
  {
    try
    {
      return object.getClass().getMethod(getter).invoke(object);
    }
    catch (NoSuchMethodException e)
    {
      throw new com.tn.jinq.collections.IndexException("The getter '" + getter + "' cannot be found.", e);
    }
    catch (InvocationTargetException e)
    {
      throw new com.tn.jinq.collections.IndexException("An error occurred invoking the getter '" + getter + "'.", e);
    }
    catch (IllegalAccessException e)
    {
      throw new com.tn.jinq.collections.IndexException("The getter '" + getter + "' cannot be accessed.", e);
    }
  }

  /**
   * Returns the indexed values for the <code>getter</code>.
   */
  private Map<Object, Set<T>> getIndexedValues(String getter)
  {     
    Map<Object, Set<T>> indexedValues = this.indexes.get(getter);

    if (indexedValues == null)
    {
      indexedValues = new HashMap<Object, Set<T>>();
      this.indexes.put(getter, indexedValues);
    }

    return indexedValues;
  }

  /**
   * Returns the values for the <code>indexKey</code>.
   */
  private Set<T> getValues(Map<Object, Set<T>> indexedValues, Object indexKey)
  {
    Set<T> values = indexedValues.get(indexKey);
    if (values == null)
    {
      values = new HashSet<T>();
      indexedValues.put(indexKey, values);
    }

    return values;
  }
}
