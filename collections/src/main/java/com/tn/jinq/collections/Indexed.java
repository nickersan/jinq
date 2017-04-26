package com.tn.jinq.collections;

import java.util.Collection;

/**
 * Implemented by objects that support indexing.
 */
public interface Indexed<T>
{
  /**
   * Returns all the items held by the indexed object.
   */
  public Collection<T> getAllItems();

  /**
   * Returns <code>true</code> if an index exists for the <code>getter</code>; otherwise <code>false</code>.
   */
  public boolean hasIndex(String getter);

  /**
   * Returns the index items for the <code>getter</code> and <code>indexKey</code> or <code>null</code> if there's no index of the
   * <code>getter</code>.
   */
  public Collection<T> getIndexedItems(String getter, Object indexKey);

  /**
   * Adds an index for the specified <code>getter</code>.
   */
  public void addIndex(String getter);

  /**
   * Removes an index for the specified <code>getter</code>.
   */
  public void removeIndex(String getter);
}
