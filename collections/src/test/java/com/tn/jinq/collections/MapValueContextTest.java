package com.tn.jinq.collections;

import static org.junit.Assert.*;
import static com.tn.jinq.predicate.ComparisonPredicate.*;
import static com.tn.jinq.predicate.LogicPredicate.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.junit.Test;

import com.tn.jinq.Keyed;
import com.tn.jinq.Queryable;
import com.tn.jinq.predicate.Predicate;

/**
 * Test cases for <code>MapValueContext</code>.
 */
public class MapValueContextTest
{
  /**
   * Tests a get using a <code>MapValueContext</code>.
   */
  @Test
  public void testGet() throws Exception
  {
    TestBean one = new TestBean(1, "One");
    TestBean two = new TestBean(2, "Two");
    TestBean three = new TestBean(3, "Three");
    TestBean four = new TestBean(4, "Four");
    TestBean five = new TestBean(5, "Five");

    Map<Long, TestBean> testSubjects = new HashMap<Long, TestBean>();
    testSubjects.put(one.getId(), one);
    testSubjects.put(two.getId(), two);
    testSubjects.put(three.getId(), three);
    testSubjects.put(four.getId(), four);
    testSubjects.put(five.getId(), five);

    Keyed<Long, TestBean> testSubjectDataSource = new MapValueContext<Long, TestBean, Predicate>(testSubjects);

    for (Long id : testSubjects.keySet())
    {
      assertEquals(
        testSubjects.get(id),
        testSubjectDataSource.get(id)
      );
    }
  }

  /**
   * Tests a query using a <code>MapValueContext</code>.
   */
  @Test
  public void testQuery() throws Exception
  {
    TestBean one = new TestBean(1, "One");
    TestBean two = new TestBean(2, "Two");
    TestBean three = new TestBean(3, "Three");
    TestBean four = new TestBean(4, "Four");
    TestBean five = new TestBean(5, "Five");

    Map<Long, TestBean> testSubjects = new HashMap<Long, TestBean>();
    testSubjects.put(one.getId(), one);
    testSubjects.put(two.getId(), two);
    testSubjects.put(three.getId(), three);
    testSubjects.put(four.getId(), four);
    testSubjects.put(five.getId(), five);

    Queryable<TestBean, Predicate> testSubjectDataSource = new MapValueContext<Long, TestBean, Predicate>(testSubjects);

    assertEquals(
      Arrays.asList(one, two, three),
      testSubjectDataSource.select(or(eq("getValue", "One"), eq("getValue", "Two"), eq("getValue", "Three")))
    );
  }

  /**
   * A bean for use with the tests.
   */
  public class TestBean
  {
    private long id;
    private String value;

    /**
     * Creates a new <code>TestBean</code>
     *
     * @param id the id.
     * @param value the value.
     */
    public TestBean(long id, String value)
    {
      this.id = id;
      this.value = value;
    }

    /**
     * Returns the id.
     */
    public long getId()
    {
      return id;
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
