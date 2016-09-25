package com.tn.jinq.predicate;

/**
 * An implementation of <code>Predicate</code> that defines basic <i>logic</i> symantics.
 */
public class LogicPredicate implements Predicate
{
  /**
   * The types of logic operations available.
   */
  public enum Type
  {
    AND,
    OR,
    NOT
  }

  private Predicate[] predicates;
  private Type type;

  /**
   * Creates a new <i>and</i> <code>LogicPredicate</code>.
   *
   * @param predicates the predicates to combine with <i>and</i> logic.
   *
   * @return the new <code>LogicPredicate</code>.
   */
  public static Predicate and(Predicate... predicates)
  {
    return new LogicPredicate(Type.AND, predicates);
  }

  /**
   * Creates a new <i>or</i> <code>LogicPredicate</code>.
   *
   * @param predicates the predicates to combine with <i>or</i> logic.
   *
   * @return the new <code>LogicPredicate</code>.
   */
  public static Predicate or(Predicate... predicates)
  {
    return new LogicPredicate(Type.OR, predicates);
  }

  /**
   * Creates a new <i>not</i> <code>LogicPredicate</code>.
   *
   * @param predicate the predicates to apply <i>not</i> logic to.
   *
   * @return the new <code>LogicPredicate</code>.
   */
  public static Predicate not(Predicate predicate)
  {
    return new LogicPredicate(Type.NOT, predicate);
  }

  /**
   * Creates a new <code>ComparisonPredicate</code>.
   *
   * @param type       the type of comparison.
   * @param predicates the predicates to apply the logic to.
   */
  private LogicPredicate(Type type, Predicate... predicates)
  {
    this.type = type;
    this.predicates = predicates;
  }

  /**
   * Returns the predicates.
   */
  public Predicate[] getPredicates()
  {
    return predicates;
  }

  /**
   * Returns the type.
   */
  public Type getType()
  {
    return type;
  }
}
