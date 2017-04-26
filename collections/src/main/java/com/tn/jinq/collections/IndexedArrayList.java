package com.tn.jinq.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tn.jinq.collections.util.IndexSupport;

/**
 *
 */
public class IndexedArrayList<T> extends ArrayList<T> implements Indexed<T>
{
  private IndexSupport<T> indexSupport = new IndexSupport<T>(this);

  /**
   * {@inheritDoc}
   */
  public IndexedArrayList()
  {
    super();
  }

  /**
   * {@inheritDoc}
   */
  public IndexedArrayList(Collection<? extends T> c)
  {
    super(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T set(int index, T element)
  {
    indexSupport.index(element);
    return super.set(index, element);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<T> getAllItems()
  {
    return indexSupport.getAllItems();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasIndex(String getter)
  {
    return indexSupport.hasIndex(getter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<T> getIndexedItems(String getter, Object indexKey)
  {
    return indexSupport.getIndexedItems(getter, indexKey);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(T element)
  {
    indexSupport.index(element);
    return super.add(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(int index, T element)
  {
    indexSupport.index(element);
    super.add(index, element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(Collection<? extends T> collection)
  {
    indexSupport.indexAll(collection);
    return super.addAll(collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(int index, Collection<? extends T> collection)
  {
    indexSupport.indexAll(collection);
    return super.addAll(index, collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addIndex(String getter)
  {
    indexSupport.addIndex(getter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear()
  {
    indexSupport.unindexAll(this);
    super.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T remove(int index)
  {
    T element = super.remove(index);
    indexSupport.unindex(element);
    return element;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(Object element)
  {
    //noinspection unchecked
    indexSupport.unindex((T)element);
    return super.remove(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void removeRange(int fromIndex, int toIndex)
  {
    indexSupport.unindexAll(subList(fromIndex, toIndex));
    super.removeRange(fromIndex, toIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeAll(Collection<?> collection)
  {
    //noinspection unchecked
    indexSupport.unindexAll((Collection<T>)collection);
    return super.removeAll(collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(Collection<?> collection)
  {
    List<T> removedElements = new ArrayList<T>(this);
    removedElements.removeAll(collection);
    indexSupport.unindexAll(removedElements);    
    return super.retainAll(collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeIndex(String getter)
  {
    indexSupport.removeIndex(getter);
  }
}
