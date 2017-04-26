package com.tn.jinq.collections;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Writable;

/**
 * Test cases for <code>CollectionContext</code>.
 */
public class CollectionContextTest
{
  /**
   * Tests a call to <code>CollectionContext.write(T)</code>.
   */
  @Test
  public void testWrite() throws Exception 
  {
    List<String> list = new ArrayList<String>();

    String s = "The quick brown fox.";

    Writable<String> collectionContext = new CollectionContext<String, Predicate>(list);
    collectionContext.write(s);

    assertTrue(list.contains(s));
  }
}
