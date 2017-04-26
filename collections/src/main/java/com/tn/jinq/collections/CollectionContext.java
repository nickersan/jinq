package com.tn.jinq.collections;

import java.util.Collection;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Writable;
import com.tn.jinq.WriteException;

/**
 * A specialization of <code>IterableContext</code> that adds the behaviour associated with a <code>Writable</code>. 
 */
public class CollectionContext<T, P extends Predicate> extends IterableContext<T, P> implements Writable<T>
{
  private Collection<T> collection;

  /**
   * Creates a new <code>CollectionContext</code>.
   *
   * @param collection the <code>Collection</code> to use within this <code>Context</code>.
   */
  public CollectionContext(Collection<T> collection)
  {
    super(collection);
    this.collection = collection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(T value) throws WriteException
  {
    collection.add(value);
  }
}
