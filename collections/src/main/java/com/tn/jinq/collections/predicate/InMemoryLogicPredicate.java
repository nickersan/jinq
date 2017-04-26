package com.tn.jinq.collections.predicate;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

import com.tn.jinq.predicate.LogicPredicate;
import com.tn.jinq.predicate.PredicateInterpreter;
import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.collections.Indexed;

/**
 * A specialization of <code>InMemoryPredicate</code> that performs <i>logic</i> operations.
 */
public class InMemoryLogicPredicate extends InMemoryPredicate
{
  private List<InMemoryPredicate>                 childPredicates;
  private PredicateInterpreter<InMemoryPredicate> predicateInterpreter;
  private LogicPredicate                          logicPredicate;

  /**
   * Creates a new <code>InMemoryComparisonPredicate</code> initialized with the <code>predicateInterpreter</code> and the
   * <code>LogicPredicate</code>.
   */
  public InMemoryLogicPredicate(PredicateInterpreter<InMemoryPredicate> predicateInterpreter, LogicPredicate logicPredicate)
  {
    this.predicateInterpreter = predicateInterpreter;
    this.logicPredicate       = logicPredicate;
    this.childPredicates      = interpretChildPredicates(logicPredicate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Collection<T> getQueryableElements(Indexed<T> indexed)
  {
    Set<T> queryableElements = new HashSet<T>();

    for (InMemoryPredicate childPredicate : childPredicates)
    {
      queryableElements.addAll(childPredicate.getQueryableElements(indexed));
    }

    return queryableElements;
  }

  /**
   * {@inheritDoc}
   */
  public boolean evaluate(Object object) throws PredicateEvaluationException
  {
    switch (logicPredicate.getType())
    {
      case AND:
        return and(object, childPredicates);

      case OR:
        return or(object, childPredicates);

      case NOT:
        return not(object, childPredicates);

      default:
        throw new PredicateEvaluationException("Unhandled logic type '" + logicPredicate.getType() + "'.");
    }
  }

  /**
   * Returns <code>true</code> the <code>object</code> evaluates to <code>true</code> for all the <code>inMemoryPredicates</code>; otherwise
   * <code>false</code>.
   */
  private boolean and(Object object, List<InMemoryPredicate> inMemoryPredicates)
  {
    for (InMemoryPredicate inMemoryPredicate : inMemoryPredicates)
    {
      if (!inMemoryPredicate.evaluate(object))
      {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns <code>true</code> the <code>object</code> evaluates to <code>true</code> for any of the <code>inMemoryPredicates</code>;
   * otherwise <code>false</code>.
   */
  private boolean or(Object object, List<InMemoryPredicate> inMemoryLogicPredicates)
  {
    for (InMemoryPredicate inMemoryPredicate : inMemoryLogicPredicates)
    {
      if (inMemoryPredicate.evaluate(object))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns <code>true</code> the <code>object</code> evaluates to <code>false</code> for all the <code>inMemoryPredicates</code>;
   * otherwise <code>false</code>.
   */
  private boolean not(Object object, List<InMemoryPredicate> inMemoryLogicPredicates)
  {
    for (InMemoryPredicate inMemoryPredicate : inMemoryLogicPredicates)
    {
      if (inMemoryPredicate.evaluate(object))
      {
        return false;
      }
    }

    return true;
  }

  /**
   * Interprets each of the <code>logicPredicate</code>'s child <code>Predicate</code>s.
   */
  private List<InMemoryPredicate> interpretChildPredicates(LogicPredicate logicPredicate)
  {
    List<InMemoryPredicate> inMemoryPredicates = new ArrayList<InMemoryPredicate>();
    for (Predicate predicate : logicPredicate.getPredicates())
    {
      inMemoryPredicates.add(predicateInterpreter.interpret(predicate));
    }

    return inMemoryPredicates;
  }
}
