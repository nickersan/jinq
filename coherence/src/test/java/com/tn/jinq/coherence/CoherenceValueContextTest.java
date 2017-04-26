package com.tn.jinq.coherence;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/**
 * Test cases for <code>CoherenceValueContext</code>.
 */
@SuppressWarnings({"unchecked"})
public class CoherenceValueContextTest
{

  @Test
  public void testGet() throws Exception
  {

    TestSubject one = new TestSubject(1, "One");
    TestSubject two = new TestSubject(2, "Two");
    TestSubject three = new TestSubject(3, "Three");
    TestSubject four = new TestSubject(4, "Four");
    TestSubject five = new TestSubject(5, "Five");

    NamedCache testSubjects = CacheFactory.getCache("local-test-subject-cache");
    testSubjects.put(one.getId(), one);
    testSubjects.put(two.getId(), two);
    testSubjects.put(three.getId(), three);
    testSubjects.put(four.getId(), four);
    testSubjects.put(five.getId(), five);

    Keyed<Long, TestSubject> testSubjectContext =
      new CoherenceValueContext<Long, TestSubject, Expression>(testSubjects);

    for (Object id : testSubjects.keySet())
    {

      assertEquals(
        testSubjects.get(id),
        testSubjectContext.get((Long) id)
      );
    }
  }

  @Test
  public void testQuery() throws Exception
  {

    TestSubject one = new TestSubject(1, "One");
    TestSubject two = new TestSubject(2, "Two");
    TestSubject three = new TestSubject(3, "Three");
    TestSubject four = new TestSubject(4, "Four");
    TestSubject five = new TestSubject(5, "Five");

    NamedCache testSubjects = CacheFactory.getCache("local-test-subject-cache");
    testSubjects.put(one.getId(), one);
    testSubjects.put(two.getId(), two);
    testSubjects.put(three.getId(), three);
    testSubjects.put(four.getId(), four);
    testSubjects.put(five.getId(), five);

    Queryable<TestSubject, Expression> testSubjectContext =
      new CoherenceValueContext<Long, TestSubject, Expression>(testSubjects);

    assertEquals(
      Arrays.asList(one, two, three),
      TestUtils.asList(
        testSubjectContext.select(
          or(eq("getId", one.getId()), eq("getId", two.getId()), eq("getId", three.getId()))
        )
      )
    );
  }

  @Test
  public void testWrite() throws Exception
  {

    TestSubject testSubject = new TestSubject(1, "One");
    NamedCache testSubjects = CacheFactory.getCache("local-test-subject-cache");

    KeyedWritable<Long, TestSubject> testSubjectContext =
      new CoherenceValueContext<Long, TestSubject, Expression>(testSubjects);

    testSubjectContext.write(testSubject.getId(), testSubject);

    assertEquals(
      testSubject,
      testSubjects.get(testSubject.getId())
    );
  }

  @Test
  public void testEvent() throws Exception
  {

    final TestSubject testSubject = new TestSubject(1, "One");
    NamedCache testSubjects = CacheFactory.getCache("local-test-subject-cache");

    CoherenceValueContext<Long, TestSubject, Expression> testSubjectContext =
      new CoherenceValueContext<Long, TestSubject, Expression>(testSubjects);

    final int[] eventCount = {0};

    Observer<TestSubject> observer = new Observer<TestSubject>()
    {
      @Override
      public void onNext(TestSubject value)
      {
        assertEquals(
          testSubject,
          value
        );
        eventCount[0]++;
      }
    };

    //Add the observer twice because of the way Coherence handles put
    testSubjectContext.subscribe(observer, ge("getId", 0l));
    testSubjectContext.subscribe(observer, gt("getId", 0l));

    testSubjectContext.write(testSubject.getId(), testSubject);

    assertEquals(1, eventCount[0]);
  }
}
