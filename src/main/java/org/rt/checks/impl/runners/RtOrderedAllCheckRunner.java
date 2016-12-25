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

import org.reflections.Reflections;
import org.rt.checks.RtCheckRunner;
import org.rt.checks.annotation.RtCheck;
import org.rt.checks.annotation.RtChecker;
import org.rt.checks.impl.comparators.RtCheckPriorityComparator;
import org.rt.checks.impl.comparators.RtCheckerLevelComparator;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Run checks with ordered checkers
 */
public class RtOrderedAllCheckRunner extends RtListCheckRunner {
  private Comparator<RtCheck> checkComparator;

  /**
   * Constructor
   *
   * @param packagePrefix Checkers package prefix
   * @param comparator    Ordering comparator
   */
  public RtOrderedAllCheckRunner(String packagePrefix, Comparator<RtChecker> comparator) {
    super(findCheckClasses(packagePrefix, comparator));
  }

  /**
   * Constructor
   *
   * @param packagePrefix     Checkers package prefix
   * @param checkerComparator Ordering checkers comparator
   * @param checkComparator   Ordering checkers checks comparator
   */
  public RtOrderedAllCheckRunner(String packagePrefix, Comparator<RtChecker> checkerComparator, Comparator<RtCheck> checkComparator) {
    super(findCheckClasses(packagePrefix, checkerComparator));
    this.checkComparator = checkComparator;
  }

  /**
   * Constructor with natural checks ordering
   *
   * @param packagePrefix Checkers package prefix
   */
  public RtOrderedAllCheckRunner(String packagePrefix) {
    this(packagePrefix, new RtCheckerLevelComparator(), new RtCheckPriorityComparator());
  }

  /**
   * Find classes in package
   *
   * @param packagePrefix     Package prefix
   * @param checkerComparator Comparator for checkers
   * @return Checkers classes
   */
  private static Collection<Class<?>> findCheckClasses(String packagePrefix, final Comparator<RtChecker> checkerComparator) {
    Reflections reflections = new Reflections(packagePrefix);
    Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RtChecker.class);

    List<Class<?>> result = new ArrayList<Class<?>>(classes);
    Collections.sort(result, new Comparator<Class<?>>() {
      public int compare(Class<?> o1, Class<?> o2) {
        return checkerComparator.compare(o1.getAnnotation(RtChecker.class), o2.getAnnotation(RtChecker.class));
      }
    });

    return result;
  }

  /**
   * Return runner instance for checker class
   *
   * @param aClass Checker class
   * @return Runner instance
   */
  @Override
  protected RtCheckRunner getRunnerFor(Class<?> aClass) {
    return new RtConcreteCheckRunner(aClass) {
      @Override
      protected Collection<Method> getMethods(Class aClass) {
        Collection<Method> methods = super.getMethods(aClass);
        List<Method> result = new ArrayList<Method>(methods);
        Collections.sort(result, new Comparator<Method>() {
          public int compare(Method o1, Method o2) {
            return checkComparator.compare(o1.getAnnotation(RtCheck.class), o2.getAnnotation(RtCheck.class));
          }
        });
        return result;
      }
    };
  }
}
