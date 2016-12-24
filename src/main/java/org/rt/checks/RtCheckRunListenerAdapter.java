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
package org.rt.checks;

import org.rt.checks.annotation.RtCheck;
import org.rt.checks.annotation.RtChecker;

import javax.annotation.Nullable;

/**
 * Simple realization of {@link RtCheckRunListener} with empty methods
 *
 * @author dsaponenko
 */
public class RtCheckRunListenerAdapter implements RtCheckRunListener {
  /**
   * Setup invoke for every RtChecker class
   *
   * @param checker Checker instance
   * @see RtChecker
   */
  public void setUp(RtChecker checker) {

  }

  /**
   * Run before every check from checker class
   *
   * @param check Check configuration
   */
  public void before(RtCheck check) {

  }

  /**
   * Main method. Invoke after every check.
   *
   * @param check       Check configuration
   * @param checkResult Check result
   */
  public void after(RtCheck check, RtCheckResult checkResult) {

  }

  /**
   * Finally method for every checker class
   *
   * @param checker Checker configuration
   */
  public void tearDown(RtChecker checker) {

  }

  /**
   * Error run checks
   *
   * @param checker Checker configuration
   * @param check   Check configuration. May be null
   * @param ex      Exception
   */
  public void error(RtChecker checker, @Nullable RtCheck check, Exception ex) {

  }
}
