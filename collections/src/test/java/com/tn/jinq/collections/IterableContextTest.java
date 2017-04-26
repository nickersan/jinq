package com.tn.jinq.collections;

import static org.junit.Assert.*;
import static com.tn.jinq.predicate.ComparisonPredicate.*;
import static com.tn.jinq.predicate.LogicPredicate.*;

import java.util.Arrays;

import org.junit.Test;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Queryable;

/**
 * Test cases for <code>IterableContext</code>.
 */
public class IterableContextTest
{
  /**
   * Tests a query using an <code>IterableContext</code>.
   */
  @Test
  public void testQuery() throws Exception
  {
    TestBean one = new TestBean("One");
    TestBean two = new TestBean("Two");
    TestBean three = new TestBean("Three");
    TestBean four = new TestBean("Four");
    TestBean five = new TestBean("Five");

    Queryable<TestBean, Predicate> testSubjectContext = new IterableContext<TestBean, Predicate>(
      Arrays.asList(one, two, three, four, five)
    );

    assertEquals(
      Arrays.asList(one, two, three),
      testSubjectContext.select(or(eq("getValue", "One"), eq("getValue", "Two"), eq("getValue", "Three")))
    );
  }

  /**
   * A bean for use with the tests.
   */
  public class TestBean {

    private String value;

    /**
     * Creates a new <code>TestBean</code>
     *
     * @param value the value.
     */
    public TestBean(String value)
    {
      this.value = value;
    }

    /**
     * Returns the value.
     */
    public String getValue()
    {
      return value;
    }
  }
}
