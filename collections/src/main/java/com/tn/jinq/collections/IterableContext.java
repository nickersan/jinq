package com.tn.jinq.collections;

import java.util.List;
import java.util.ArrayList;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Context;
import com.tn.jinq.Queryable;
import com.tn.jinq.DataGetException;
import com.tn.jinq.collections.predicate.InMemoryPredicateInterpreter;
import com.tn.jinq.collections.predicate.InMemoryPredicate;

/**
 * A specialization of <code>Context</code> that works with any <code>Iterable</code> allowing it to be accessed as a
 * <code>Queryable</code>.
 */
public class IterableContext<T, P extends Predicate> extends Context<InMemoryPredicate> implements Queryable<T, P>
{
  private Iterable<T> iterable;

  /**
   * Creates a new <code>IterableContext</code>.
   *
   * @param iterable the <code>Iterable</code> to use within this <code>Context</code>.
   */
  public IterableContext(Iterable<T> iterable)
  {
    super(new InMemoryPredicateInterpreter());
    this.iterable = iterable;
  }

  /**
   * {@inheritDoc}
   */
  public Iterable<T> select(P predicate) throws DataGetException
  {
    InMemoryPredicate inMemoryPredicate = getPredicateInterpreter().interpret(predicate);

    @SuppressWarnings({"unchecked"})
    Iterable<T> iterable = this.iterable instanceof Indexed ?
      inMemoryPredicate.getQueryableElements((Indexed<T>)this.iterable) : this.iterable;
    
    List<T> hits = new ArrayList<T>();

    for (T t : iterable)
    {
      if (inMemoryPredicate.evaluate(t))
      {
        hits.add(t);
      }
    }

    return hits;
  }
}
