package com.tn.jinq.hibernate;

import static org.junit.Assert.*;

import static com.tn.jinq.predicate.ComparisonPredicate.*;
import static com.tn.jinq.predicate.LogicPredicate.*;

import java.io.StringReader;
import java.util.List;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tn.jinq.predicate.Predicate;
import com.tn.jinq.Queryable;
import com.tn.jinq.Writable;

/**
 * Test cases for <code>HibernateContext</code>.
 */
public class HibernateContextTest
{
  private SessionFactory sessionFactory;

  /**
   * Tests a <i>query</i> using the <code>HibernateContext</code>.
   */
  @Test
  public void testQuery() throws Exception
  {
    TestBean one = new TestBean(1, "One");
    TestBean two = new TestBean(2, "Two");
    TestBean three = new TestBean(3, "Three");
    TestBean four = new TestBean(4, "Four");
    TestBean five = new TestBean(5, "Five");

    List<TestBean> testSubjects = Arrays.asList(one, two, three, four, five);

    Session session = sessionFactory.openSession();
    try
    {
      for (TestBean testSubject : testSubjects)
      {
        session.save(testSubject);
      }
    }
    finally
    {
      session.close();
    }

    Queryable<TestBean, Predicate> testSubjectDataSource = new HibernateContext<TestBean, Predicate>(
      sessionFactory,
      TestBean.class
    );

    assertEquals(
      Arrays.asList(one, two, five),
      testSubjectDataSource.select(
        or(eq("getValue", one.getValue()), eq("getValue", two.getValue()), eq("getValue", five.getValue()))
      )
    );
  }

  /**
   * Tests a <i>write</i> using the <code>HibernateContext</code>.
   */
  @Test
  public void testWrite() throws Exception
  {
    TestBean testSubject = new TestBean(1, "One");

    Writable<TestBean> testSubjectDataSource = new HibernateContext<TestBean, Predicate>(
      sessionFactory,
      TestBean.class
    );
    testSubjectDataSource.write(testSubject);

    Session session = sessionFactory.openSession();
    try
    {
      assertEquals(
        testSubject,
        session.createCriteria(TestBean.class).add(Restrictions.eq("id", testSubject.getId())).list().get(0)
      );
    }
    finally
    {
      session.close();
    }
  }

  /**
   * Initializes a Hibernate session before each test.
   */
  @Before
  public void before() throws Exception
  {
    Document hibernateConfig = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
      new InputSource(
        new StringReader(
          "<hibernate-configuration>" +
            "<session-factory>" +
            "<property name=\"connection.driver_class\">org.hsqldb.jdbcDriver</property>" +
            "<property name=\"connection.url\">jdbc:hsqldb:mem:mymemdb</property>" +
            "<property name=\"connection.username\">sa</property>" +
            "<property name=\"connection.password\"></property>" +
            "<property name=\"connection.pool_size\">1</property>" +
            "<property name=\"dialect\">org.hibernate.dialect.HSQLDialect</property>" +
            "<property name=\"current_session_context_class\">thread</property>" +
            "<property name=\"cache.provider_class\">org.hibernate.cache.NoCacheProvider</property>" +
            "<property name=\"hbm2ddl.auto\">create-drop</property>" +
            "<property name=\"show_sql\">true</property>" +
            "</session-factory>" +
            "</hibernate-configuration>"
        )
      )
    );

    Document mapping = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
      new InputSource(
        new StringReader(
          "<hibernate-mapping>" +
            "<class name=\"" + TestBean.class.getName() + "\" table=\"SUBJECTS\">" +
            "<id name=\"id\">" +
            "<generator class=\"native\"/>" +
            "</id>" +
            "<property name=\"value\"/>" +
            "</class>" +
            "</hibernate-mapping>"
        )
      )
    );

    Configuration configuration = new Configuration().configure(hibernateConfig);
    configuration.addDocument(mapping);

    sessionFactory = configuration.buildSessionFactory();
  }

  /**
   * Cleans up the Hibernate session after each test.
   */
  @After
  public void after() throws Exception
  {
    if (sessionFactory != null)
    {
      sessionFactory.close();
    }
  }

  /**
   * A bean for use with the tests.
   */
  public static class TestBean
  {
    private int id;
    private String value;

    /**
     * Creates a new <code>TestBean</code>
     *
     * @param id the id.
     * @param value the value.
     */
    public TestBean(int id, String value)
    {
      this.id = id;
      this.value = value;
    }

    /**
     * Used by Hibernate.
     */
    protected TestBean()
    {
    }
     
    /**
     * Returns the id.
     */
    public int getId()
    {
      return id;
    }

    /**
     * Sets the id.
     */
    public void setId(int id)
    {
      this.id = id;
    }

    /**
     * Returns the value.
     */
    public String getValue()
    {
      return value;
    }

    /**
     * Sets the value.
     */
    public void setValue(String value)
    {
      this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other)
    {
      return this == other ||
        other != null &&
        getClass() == other.getClass() &&
        getId() == ((TestBean)other).getId() && 
        getValue().equals(((TestBean)other).getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
      return id;
    }
  }
}
