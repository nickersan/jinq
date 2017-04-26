package com.tn.jinq.collections;

import static org.junit.Assert.*;

import static com.tn.jinq.predicate.ComparisonPredicate.*;
import static com.tn.jinq.predicate.LogicPredicate.or;

import java.util.Arrays;
import java.util.Objects;

import org.junit.Test;

import com.tn.jinq.Queryable;
import com.tn.jinq.predicate.Predicate;

/**
 * Test cases for <code>IndexedArrayList</code>.
 */
public class IndexedArrayListTest
{
  /**
   * Tests that an index is used if it exists.
   */
  @Test
  public void testQueryUsingIndex() throws Exception
  {
    TestBean testBean1 = new TestBean("test1", "another1");
    TestBean testBean2 = new TestBean("test2", "another2");
    TestBean testBean3 = new TestBean("test3", "another3");

    IndexedArrayList<TestBean> testBeans = new IndexedArrayList<TestBean>(Arrays.asList(testBean1, testBean2, testBean3));
    testBeans.addIndex("getValue");

    Queryable<TestBean, Predicate> queryable = new CollectionContext<TestBean, Predicate>(testBeans);
    assertEquals(Arrays.asList(testBean2, testBean3), queryable.select(or(eq("getValue", "test2"), eq("getValue", "test3"))));
  }

  /**
   * Tests that the query still works if there is no index.
   */
  @Test
  public void testQueryWithNoIndex() throws Exception
  {
    TestBean testBean1 = new TestBean("test1", "another1");
    TestBean testBean2 = new TestBean("test2", "another2");
    TestBean testBean3 = new TestBean("test3", "another3");

    IndexedArrayList<TestBean> testBeans = new IndexedArrayList<TestBean>(Arrays.asList(testBean1, testBean2, testBean3));
    testBeans.addIndex("getValue");

    Queryable<TestBean, Predicate> queryable = new IterableContext<TestBean, Predicate>(testBeans);
    assertEquals(Arrays.asList(testBean2), queryable.select(eq("getAnotherValue", "another2")));
  }

  /**
   * An interface used in the tests.
   */
  public static class TestBean
  {
    private String value;
    private String anotherValue;

    /**
     * Initializes a new {@code TestBean}.
     */
    public TestBean(String value, String anotherValue)
    {
      this.value = value;
      this.anotherValue = anotherValue;
    }

    /**
     * Returns a value.
     */
    public String getValue()
    {
      return this.value;
    }

    /**
     * Returns a another value.
     */
    public String getAnotherValue()
    {
      return this.anotherValue;
    }

    /**
     *
     */
    public boolean equals(Object object)
    {
      return object != null && getClass().equals(object.getClass())
        && Objects.equals(value, ((TestBean)object).value)
        && Objects.equals(anotherValue, ((TestBean)object).anotherValue);
    }

    public int hashCode()
    {
      return Objects.hashCode(value);
    }
  }
}
