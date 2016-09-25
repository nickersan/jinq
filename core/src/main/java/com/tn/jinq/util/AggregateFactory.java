package com.tn.jinq.util;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * A factory class that creates a <i>dynamic proxy</i> for a given interface that maps method calls onto the appropriate method on the
 * underlying proxy.
 */
public class AggregateFactory
{
  /**
   * Creates a new <code>aggregateType</code> backed by the <code>context</code>.
   *
   * @throws AggregateException if the aggregateType is not an interface or the context doesn't implement the
   *                            aggregate interfaces.
   */
  public static <T> T newAggregate(final Object context, Class<T> aggregateType) throws AggregateException
  {
    if (!aggregateType.isInterface())
    {
      throw new AggregateException("The aggregateType '" + aggregateType.getName() + "' is not an interface.");
    }

    List<Class<?>> interfaces = getAllInterfaces(context.getClass());
    for (Class aggregateInterface : aggregateType.getInterfaces())
    {
      if (!interfaces.contains(aggregateInterface))
      {
        throw new AggregateException(
          "The context '" + context.getClass().getName() + "' doesn't implement '" + aggregateInterface.getClass().getName() + "'"
        );
      }
    }

    //noinspection unchecked
    return (T)Proxy.newProxyInstance(
      context.getClass().getClassLoader(),
      new Class[]{aggregateType},
      new InvocationHandler()
      {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
          return method.invoke(context, args);
        }
      }
    );
  }

  /**
   * Returns all the interfaces implemented by <code>subjectClass</code>.
   *
   * @param subjectClass the subject class.
   * @return the interfaces.
   */
  private static List<Class<?>> getAllInterfaces(Class<?> subjectClass)
  {
    List<Class<?>> interfaces = new ArrayList<Class<?>>(Arrays.asList(subjectClass.getInterfaces()));
    if (subjectClass.getSuperclass() != null)
    {
      interfaces.addAll(getAllInterfaces(subjectClass.getSuperclass()));
    }

    return interfaces;
  }
}
