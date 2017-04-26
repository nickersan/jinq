package com.tn.jinq.collections.predicate;

import java.util.Collection;

import com.tn.jinq.predicate.ComparisonPredicate;
import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.collections.Indexed;

/**
 * A specialization of <code>InMemoryPredicate</code> that performs <i>comparison</i> operations.
 */
public class InMemoryComparisonPredicate extends InMemoryPredicate
{
  private ComparisonPredicate comparisonPredicate;

  /**
   * Creates a new <code>InMemoryComparisonPredicate</code> initialized with the <code>ComparisonPredicate</code>.
   */
  public InMemoryComparisonPredicate(ComparisonPredicate comparisonPredicate)
  {
    this.comparisonPredicate = comparisonPredicate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Collection<T> getQueryableElements(Indexed<T> indexed)
  {
    return indexed.hasIndex(comparisonPredicate.getGetter()) ?
      indexed.getIndexedItems(comparisonPredicate.getGetter(), comparisonPredicate.getValue()) : indexed.getAllItems();      
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean evaluate(Object object) throws PredicateEvaluationException
  {
    switch (comparisonPredicate.getType())
    {
      case EQUALS:
        return comparisonPredicate.getValue().equals(getValue(object, comparisonPredicate.getGetter()));

      case GREATER_THAN:
        return greaterThan(comparisonPredicate.getValue(), getValue(object, comparisonPredicate.getGetter()));

      case GREATER_THAN_OR_EQUAL:
        return greaterThanOrEqual(comparisonPredicate.getValue(), getValue(object, comparisonPredicate.getGetter()));

      case LESS_THAN:
        return lessThan(comparisonPredicate.getValue(), getValue(object, comparisonPredicate.getGetter()));

      case LESS_THAN_OR_EQUAL:
        return lessThanOrEqual(comparisonPredicate.getValue(), getValue(object, comparisonPredicate.getGetter()));

      default:
        throw new PredicateEvaluationException("Unhandled comparisson type '" + comparisonPredicate.getType() + "'.");
    }
  }

  /**
   * Returns <code>true</code> if <code>value1</code> is greater than <code>value2</code>; otherwise <code>false</code>.
   *
   * @throws PredicateEvaluationException if both values are not instances of <code>Comparable</code>.
   */
  private boolean greaterThan(Object value1, Object value2) throws PredicateEvaluationException
  {
    if (value1 instanceof Comparable && value2 instanceof Comparable)
    {
      //noinspection unchecked
      return ((Comparable<Object>)value1).compareTo(value2) < 0;
    }
    else
    {
      throw new PredicateEvaluationException("Only objects that implement Comparable can be used in ComparisonExpressions.");
    }
  }

  /**
   * Returns <code>true</code> if <code>value1</code> is greater than or equal to <code>value2</code>; otherwise <code>false</code>.
   *
   * @throws PredicateEvaluationException if both values are not instances of <code>Comparable</code>.
   */
  private boolean greaterThanOrEqual(Object value1, Object value2) throws PredicateEvaluationException
  {
    if (value1 instanceof Comparable && value2 instanceof Comparable)
    {
      //noinspection unchecked
      return ((Comparable<Object>) value1).compareTo(value2) <= 0;
    }
    else
    {
      throw new PredicateEvaluationException("Only objects that implement Comparable can be used in ComparisonExpressions.");
    }
  }

  /**
   * Returns <code>true</code> if <code>value1</code> is less than <code>value2</code>; otherwise <code>false</code>.
   */
  private boolean lessThan(Object value1, Object value2) throws PredicateEvaluationException
  {
    if (value1 instanceof Comparable && value2 instanceof Comparable)
    {
      //noinspection unchecked
      return ((Comparable<Object>) value1).compareTo(value2) > 0;
    }
    else
    {
      throw new PredicateEvaluationException("Only objects that implement Comparable can be used in ComparisonExpressions.");
    }
  }

  /**
   * Returns <code>true</code> if <code>value1</code> is less than or equal to <code>value2</code>; otherwise <code>false</code>.
   */
  private boolean lessThanOrEqual(Object value1, Object value2) throws PredicateEvaluationException
  {
    if (value1 instanceof Comparable && value2 instanceof Comparable)
    {
      //noinspection unchecked
      return ((Comparable<Object>) value1).compareTo(value2) >= 0;
    }
    else
    {
      throw new PredicateEvaluationException("Only objects that implement Comparable can be used in ComparisonExpressions.");
    }
  }
}
