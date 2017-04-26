package com.tn.jinq.collections.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Test cases for <code>IndexSupport</code>.
 */
public class IndexSupportTest
{
  /**
   * Tests adding an index.
   */
  @Test
  public void testAddingIndex() throws Exception 
  {
    TestBean testBean1 = new TestBean("Test");
    TestBean testBean2 = new TestBean("Test");
    TestBean testBean3 = new TestBean("Testing");

    String getter = "getValue";

    IndexSupport<TestBean> indexSupport = new IndexSupport<TestBean>(Arrays.asList(testBean1, testBean2, testBean3));
    indexSupport.addIndex(getter);

    assertEquals(
      new HashSet<TestBean>(Arrays.asList(testBean1, testBean2)),
      indexSupport.getIndexedItems(getter, "Test")
    );
  }

  /**
   * Tests adding a bean when an index already exists.
   */
  @Test
  public void testIndexingValue() throws Exception
  {
    TestBean testBean = new TestBean("Test");

    String getter = "getValue";

    IndexSupport<TestBean> indexSupport = new IndexSupport<TestBean>(new ArrayList<TestBean>());
    indexSupport.addIndex(getter);
    indexSupport.index(testBean);

    assertEquals(
      new HashSet<TestBean>(Arrays.asList(testBean)),
      indexSupport.getIndexedItems(getter, "Test")
    );

    indexSupport.unindex(testBean);
    assertFalse(indexSupport.getIndexedItems(getter, "Test").iterator().hasNext());
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
