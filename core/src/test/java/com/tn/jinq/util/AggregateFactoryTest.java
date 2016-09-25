package com.tn.jinq.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.tn.jinq.Queryable;
import com.tn.jinq.Writable;
import com.tn.jinq.DataGetException;
import com.tn.jinq.WriteException;
import com.tn.jinq.predicate.Predicate;

/**
 * Test cases for <code>AggregateFactory</code>.
 */
public class AggregateFactoryTest
{
  /**
   * Tests the successful construction of an aggregate.
   */
  @Test
  public void testSuccessfulFactoryConstruction() throws Exception
  {
    String expected = "The quick brown fox.";
    
    StringContext context = AggregateFactory.newAggregate(new TestStringContext(), StringContext.class);
    context.write(expected);
    assertEquals(expected, context.select(null).iterator().next());
  }

  /**
   * Tests an exception is thrown when the target object does not implement required interfaces.
   */
  @Test
  public void testInterfaceNotImplemented() throws Exception
  {
    try
    {
      AggregateFactory.newAggregate(new Object(), StringContext.class);
      fail("No exception thrown when the target object does not implement required interfaces.");
    }
    catch (AggregateException e)
    {
      // expected.
    }
  }

  /**
   * The aggregate interface.
   */
  private interface StringContext extends Queryable<String, Predicate>, Writable<String>{}

  /**
   * An implementation of <code>Queryable</code> and <code>Writable</code> to use in the tests.
   */
  private class TestStringContext implements Queryable<String, Predicate>, Writable<String>
  {
    private String expected;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<String> select(Predicate predicate) throws DataGetException
    {
      return Arrays.asList(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(String expected) throws WriteException
    {
      this.expected = expected;
    }
  } 
}
