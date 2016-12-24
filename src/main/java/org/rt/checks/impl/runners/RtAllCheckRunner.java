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
import org.rt.checks.annotation.RtChecker;

import java.util.Collection;

/**
 * This is class run all {@link RtChecker}s in package
 *
 * @author dsaponenko
 * @see RtChecker
 */
public class RtAllCheckRunner extends RtListCheckRunner {
  /**
   * Constructor
   *
   * @param packagePrefix Find checkers in sub packages
   */
  public RtAllCheckRunner(String packagePrefix) {
    super(findCheckClasses(packagePrefix));
  }

  /**
   * Find checkers in package by reflections
   *
   * @param packagePrefix Package prefix
   * @return Found checker classes
   */
  private static Collection<Class<?>> findCheckClasses(String packagePrefix) {
    Reflections reflections = new Reflections(packagePrefix);
    return reflections.getTypesAnnotatedWith(RtChecker.class);
  }
}
