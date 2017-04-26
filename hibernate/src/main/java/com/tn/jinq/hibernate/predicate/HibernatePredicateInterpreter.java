package com.tn.jinq.hibernate.predicate;

import java.util.Arrays;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.tn.jinq.predicate.PredicateInterpreter;
import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.predicate.ComparisonPredicate;
import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.predicate.LogicPredicate;

/**
 * An implementation of <code>PredicateInterpreter</code> that converts <code>Predicate</code>s into <code>Criterion</code>.
 */
public class HibernatePredicateInterpreter implements PredicateInterpreter<Criterion>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public Criterion interpret(Predicate predicate) throws PredicateEvaluationException
  {
    if (predicate instanceof ComparisonPredicate)
    {
      switch (((ComparisonPredicate) predicate).getType())
      {
        case EQUALS:
          return Restrictions.eq(
            toAttributeName(((ComparisonPredicate) predicate).getGetter()),
            ((ComparisonPredicate) predicate).getValue()
          );

        case GREATER_THAN:
          return Restrictions.gt(
            toAttributeName(((ComparisonPredicate) predicate).getGetter()),
            ((ComparisonPredicate) predicate).getValue()
          );

        case GREATER_THAN_OR_EQUAL:
          return Restrictions.ge(
            toAttributeName(((ComparisonPredicate) predicate).getGetter()),
            ((ComparisonPredicate) predicate).getValue()
          );

        case LESS_THAN:
          return Restrictions.lt(
            toAttributeName(((ComparisonPredicate) predicate).getGetter()),
            ((ComparisonPredicate) predicate).getValue()
          );


        case LESS_THAN_OR_EQUAL:
          return Restrictions.le(
            toAttributeName(((ComparisonPredicate) predicate).getGetter()),
            ((ComparisonPredicate) predicate).getValue()
          );
      }
    }
    else if (predicate instanceof LogicPredicate)
    {
      switch (((LogicPredicate) predicate).getType())
      {
        case AND:
          return and(((LogicPredicate) predicate).getPredicates());

        case OR:
          return or(((LogicPredicate) predicate).getPredicates());
      }
    }

    throw new PredicateEvaluationException(predicate.getClass().getName() + " cannot be interpreted.");
  }

  /**
   * Creates an <i>and</i> <code>Criterion</code> from the <code>predicates</code>.
   */
  private Criterion and(Predicate[] predicates)
  {
    if (predicates.length > 2)
    {
      Predicate predicate = predicates[0];
      return Restrictions.and(
        interpret(predicate),
        interpret(LogicPredicate.and(Arrays.copyOfRange(predicates, 1, predicates.length)))
      );
    }
    else if (predicates.length == 2)
    {
      return Restrictions.and(
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
   * Creates an <i>or</i> <code>Criterion</code> from the <code>predicates</code>.
   */
  private Criterion or(Predicate[] predicates)
  {
    if (predicates.length > 2)
    {
      Predicate predicate = predicates[0];
      return Restrictions.or(
        interpret(predicate),
        interpret(LogicPredicate.or(Arrays.copyOfRange(predicates, 1, predicates.length)))
      );
    }
    else if (predicates.length == 2)
    {
      return Restrictions.or(
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
   * Converts the <code>getter</code> to a attribute name as used by Hibernate.
   */
  private String toAttributeName(String getter) throws PredicateEvaluationException
  {
    StringBuffer attributeName = null;

    for (char c : getter.toCharArray())
    {
      if (attributeName != null)
      {
        attributeName.append(c);
      }
      else if (Character.isUpperCase(c))
      {
        attributeName = new StringBuffer();
        attributeName.append(Character.toLowerCase(c));
      }
    }

    if (attributeName != null)
    {
      return attributeName.toString();
    }

    throw new PredicateEvaluationException("Could not resolve attribute name for getter '" + getter + "'.");
  }
}
