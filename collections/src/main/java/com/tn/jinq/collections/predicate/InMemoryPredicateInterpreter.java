package com.tn.jinq.collections.predicate;

import com.tn.jinq.predicate.PredicateInterpreter;
import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.predicate.PredicateEvaluationException;
import com.tn.jinq.predicate.ComparisonPredicate;
import com.tn.jinq.predicate.LogicPredicate;

/**
 * An implementation of <code>PredicateInterpreter</code> that creates <code>InMemoryPredicate</code>s.
 */
public class InMemoryPredicateInterpreter implements PredicateInterpreter<InMemoryPredicate>
{
  /**
   * {@inheritDoc}
   */
  public InMemoryPredicate interpret(Predicate predicate) throws PredicateEvaluationException
  {
    if (predicate instanceof ComparisonPredicate)
    {
      return new InMemoryComparisonPredicate((ComparisonPredicate)predicate);
    }
    else if (predicate instanceof LogicPredicate)
    {
      return new InMemoryLogicPredicate(this, (LogicPredicate)predicate);
    }
    else
    {
      throw new PredicateEvaluationException(predicate.getClass().getName() + " cannot be interpreted.");
    }
  }
}
