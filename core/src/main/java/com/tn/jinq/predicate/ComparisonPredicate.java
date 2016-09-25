package com.tn.jinq.predicate;

/**
 * An implementation of <code>Predicate</code> that defines basic <i>comparison</i> symantics.
 */
public class ComparisonPredicate implements Predicate
{
  /**
   * The types of comparison available.
   */
  public static enum Type
  {
    EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL
  }

  private String getter;
  private Type type;
  private Object value;

  /**
   * Creates a new <i>equal</i> <code>ComparisonPredicate</code>.
   *
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   *
   * @return the new <code>ComparisonPredicate</code>.
   */
  public static ComparisonPredicate eq(String getter, Object value)
  {
    return new ComparisonPredicate(Type.EQUALS, getter, value);
  }

  /**
   * Creates a new <i>greater than</i> <code>ComparisonPredicate</code>.
   *
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   *
   * @return the new <code>ComparisonPredicate</code>.
   */
  public static ComparisonPredicate gt(String getter, Object value)
  {
    return new ComparisonPredicate(Type.GREATER_THAN, getter, value);
  }

  /**
   * Creates a new <i>greater than or equal</i> <code>ComparisonPredicate</code>.
   *
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   *
   * @return the new <code>ComparisonPredicate</code>.
   */
  public static ComparisonPredicate ge(String getter, Object value)
  {
    return new ComparisonPredicate(Type.GREATER_THAN_OR_EQUAL, getter, value);
  }

  /**
   * Creates a new <i>less than</i> <code>ComparisonPredicate</code>.
   *
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   *
   * @return the new <code>ComparisonPredicate</code>.
   */
  public static ComparisonPredicate lt(String getter, Object value)
  {
    return new ComparisonPredicate(Type.LESS_THAN, getter, value);
  }

  /**
   * Creates a new <i>less than or equal</i> <code>ComparisonPredicate</code>.
   *
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   *
   * @return the new <code>ComparisonPredicate</code>.
   */
  public static ComparisonPredicate le(String getter, Object value)
  {
    return new ComparisonPredicate(Type.LESS_THAN_OR_EQUAL, getter, value);
  }

  /**
   * Creates a new <code>ComparisonPredicate</code>.
   *
   * @param type   the type of comparison.
   * @param getter the name of the getter to use to get the value from the object being compared.
   * @param value  the value to compare to.
   */
  private ComparisonPredicate(Type type, String getter, Object value)
  {
    this.type   = type;
    this.getter = getter;
    this.value  = value;
  }

  /**
   * Returns the getter.
   */
  public String getGetter()
  {
    return getter;
  }

  /**
   * Returns the type.
   */
  public Type getType()
  {
    return type;
  }

  /**
   * Returns the value.
   */
  public Object getValue()
  {
    return value;
  }
}
