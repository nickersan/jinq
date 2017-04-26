package com.tn.jinq.collections;

import static org.junit.Assert.assertEquals;
import static com.tn.jinq.predicate.ComparisonPredicate.*;
import static com.tn.jinq.predicate.LogicPredicate.*;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import org.junit.Test;

import com.tn.jinq.Queryable;
import com.tn.jinq.predicate.Predicate;

/**
 * Test cases for <code>MapKeyContext</code>.
 */
public class MapKeyContextTest
{
  /**
   * Tests a query using a <code>MapKeyContext</code>.
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

    Queryable<Long, Predicate> testSubjectDataSource = new MapKeyContext<Long, Predicate>(testSubjects);

    assertEquals(
      Arrays.asList(one.getId(), two.getId(), three.getId()),
      testSubjectDataSource.select(
          or(eq("longValue", one.getId()), eq("longValue", two.getId()), eq("longValue", three.getId()))
      )
    );
  }

  /**
   * A bean for use with the tests.
   */
  public class TestBean {

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
