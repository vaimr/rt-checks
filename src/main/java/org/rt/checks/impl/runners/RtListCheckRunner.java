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

import org.rt.checks.RtCheckRunListener;
import org.rt.checks.RtCheckRunner;

import java.util.Collection;

/**
 * Run all checks in transmitted {@link org.rt.checks.annotation.RtChecker} collection
 */
public class RtListCheckRunner implements RtCheckRunner {
  private Collection<Class<?>> classes;

  /**
   * Constructor
   *
   * @param classes Checker classes
   */
  public RtListCheckRunner(Collection<Class<?>> classes) {
    this.classes = classes;
  }

  /**
   * Main run method
   *
   * @param listener Process listener
   */
  public void run(RtCheckRunListener listener) {
    for (Class aClass : classes) {
      getRunnerFor(aClass).run(listener);
    }
  }

  /**
   * Return runner instance for checker class
   *
   * @param aClass Checker class
   * @return Runner instance
   */
  protected RtCheckRunner getRunnerFor(Class<?> aClass) {
    return new RtConcreteCheckRunner(aClass);
  }
}
