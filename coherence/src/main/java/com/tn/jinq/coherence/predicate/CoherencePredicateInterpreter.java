package com.tn.jinq.coherence.predicate;

import java.util.Arrays;

import com.tangosol.util.Filter;
import com.tangosol.util.filter.EqualsFilter;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.OrFilter;
import com.tangosol.util.filter.GreaterFilter;
import com.tangosol.util.filter.LessFilter;
import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.predicate.PredicateInterpreter;
import com.tn.jinq.predicate.ComparisonPredicate;
import com.tn.jinq.predicate.LogicPredicate;

/**
 * An implementation of <code>PredicateInterpreter</code> that converts <code>Predicate</code>s into <code>Filter</code>.
 */
public class CoherencePredicateInterpreter implements PredicateInterpreter<Filter>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public Filter interpret(Predicate predicate) throws PredicateEvaluationException
  {
    if (predicate instanceof ComparisonPredicate)
    {
      switch (((ComparisonPredicate)predicate).getType())
      {
        case EQUALS:
          return new EqualsFilter(
            ((ComparisonPredicate)predicate).getGetter(),
            ((ComparisonPredicate)predicate).getValue()
          );

        case GREATER_THAN:
          return new GreaterFilter(
            ((ComparisonPredicate)predicate).getGetter(),
            toComparable(((ComparisonPredicate)predicate).getValue())
          );

        case GREATER_THAN_OR_EQUAL:
          return new OrFilter(
            new GreaterFilter(
              ((ComparisonPredicate)predicate).getGetter(),
              toComparable(((ComparisonPredicate)predicate).getValue())
            ),
            new EqualsFilter(
              ((ComparisonPredicate)predicate).getGetter(),
              ((ComparisonPredicate)predicate).getValue()
            )
          );

        case LESS_THAN:
          return new LessFilter(
            ((ComparisonPredicate)predicate).getGetter(),
            toComparable(((ComparisonPredicate)predicate).getValue())
          );

        case LESS_THAN_OR_EQUAL:
          return new OrFilter(
            new LessFilter(
              ((ComparisonPredicate)predicate).getGetter(),
              toComparable(((ComparisonPredicate)predicate).getValue())
            ),
            new EqualsFilter(
              ((ComparisonPredicate)predicate).getGetter(),
              ((ComparisonPredicate)predicate).getValue()
            )
          );
      }
    }
    else if (predicate instanceof LogicPredicate)
    {
      switch (((LogicPredicate)predicate).getType())
      {
        case AND:
          return and(((LogicPredicate)predicate).getPredicates());

        case OR:
          return or(((LogicPredicate)predicate).getPredicates());
      }
    }

    throw new PredicateEvaluationException(predicate.getClass().getName() + " cannot be interpreted.");
  }

  /**
   * Creates an <i>and</i> <code>Filter</code> from the <code>predicates</code>.
   */
  private Filter and(Predicate[] predicates)
  {
    if (predicates.length > 2)
    {
      Predicate predicate = predicates[0];
      return new AndFilter(
        interpret(predicate),
        interpret(LogicPredicate.and(Arrays.copyOfRange(predicates, 1, predicates.length)))
      );
    }
    else if (predicates.length == 2)
    {
      return new AndFilter(
        interpret(predicates[0]),
        interpret(predicates[1])
      );
    }
    else if (predicates.length == 1)
    {
      //Be nice - if there's not enough parameters for an 'and' just interpret what we've got.
      return interpret(predicates[0]);
    }
    else
    {
      throw new PredicateEvaluationException("An 'and' predicate must have at least two child predicates.");
    }
  }

  /**
   * Creates an <i>or</i> <code>Filter</code> from the <code>predicates</code>.
   */
  private Filter or(Predicate[] predicates)
  {
    if (predicates.length > 2)
    {
      Predicate predicate = predicates[0];
      return new OrFilter(
        interpret(predicate),
        interpret(LogicPredicate.or(Arrays.copyOfRange(predicates, 1, predicates.length)))
      );
    }
    else if (predicates.length == 2)
    {
      return new OrFilter(
        interpret(predicates[0]),
        interpret(predicates[1])
      );
    }
    else if (predicates.length == 1)
    {
      //Be nice - if there's not enough parameters for an 'or' just interpret what we've got.
      return interpret(predicates[0]);
    }
    else
    {
      throw new PredicateEvaluationException("An 'or' predicate must have at least two child predicates.");
    }
  }

  /**
   * Returns the <code>value</code> as a <code>Comparable</code> if possible; otherwise throws a <code>PredicateEvaluationException</code>.
   */
  private Comparable toComparable(Object value)
  {
    if (value instanceof Comparable)
    {
      return (Comparable)value;
    }
    else
    {
      throw new PredicateEvaluationException("Only objects that implement Comparable can be used in ComparisonExpressions.");
    }
  }
}
