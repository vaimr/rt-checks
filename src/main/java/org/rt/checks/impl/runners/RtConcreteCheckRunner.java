/**
 * The MIT License
 * <p>
 * Copyright (c) 2016 Saponenko Denis
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.rt.checks.impl.runners;

import org.rt.checks.RtCheckResult;
import org.rt.checks.RtCheckRunListener;
import org.rt.checks.RtCheckRunner;
import org.rt.checks.annotation.RtCheck;
import org.rt.checks.annotation.RtCheckIgnore;
import org.rt.checks.annotation.RtChecker;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

/**
 * Run concrete {@link RtChecker} checks
 *
 * @author dsaponenko
 */
public class RtConcreteCheckRunner implements RtCheckRunner {
  private Class aClass;

  /**
   * Constructor
   *
   * @param aClass Checker class. Must have annotation {@link RtChecker}
   */
  public RtConcreteCheckRunner(Class aClass) {
    if (!aClass.isAnnotationPresent(RtChecker.class)) {
      throw new IllegalArgumentException(RtChecker.class.getSimpleName() + " annotation not present in class " + aClass);
    }
    this.aClass = aClass;
  }

  /**
   * Main run method
   *
   * @param listener Process listener
   */
  public void run(RtCheckRunListener listener) {
    RtCheckIgnore checkIgnore = (RtCheckIgnore) aClass.getAnnotation(RtCheckIgnore.class);
    if (checkIgnore != null) {
      return;
    }
    RtChecker checker = (RtChecker) aClass.getAnnotation(RtChecker.class);
    RtCheck check = null;
    try {
      Object instance = aClass.newInstance();
      listener.setUp(checker);
      Collection<Method> methods = getMethods(aClass);
      for (Method method : methods) {
        if (method.getAnnotation(RtCheckIgnore.class) != null) {
          return;
        }
        check = method.getAnnotation(RtCheck.class);
        listener.before(check);

        boolean isAccessible = method.isAccessible();
        method.setAccessible(true);
        RtCheckResult result;
        result = (RtCheckResult) method.invoke(instance);
        method.setAccessible(isAccessible);

        listener.after(check, result);
      }
    } catch (Exception e) {
      listener.error(checker, check, e);
    } finally {
      listener.tearDown(checker);
    }

  }

  /**
   * Return methods with annotation {@link RtCheck}
   *
   * @param aClass Checker class
   * @return Checks methods
   */
  protected Collection<Method> getMethods(Class aClass) {
    Collection<Method> result = new HashSet<Method>();
    for (Method method : aClass.getDeclaredMethods()) {
      if (!method.isAnnotationPresent(RtCheck.class)) {
        continue;
      }
      if (method.getGenericReturnType() != RtCheckResult.class) {
        throw new IllegalArgumentException("Method " + method + " should return " + RtCheckResult.class);
      }

      result.add(method);
    }
    return result;
  }
}
