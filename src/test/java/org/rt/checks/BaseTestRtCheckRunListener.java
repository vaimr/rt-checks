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

/**
 * @author dsaponenko
 */
public class BaseTestRtCheckRunListener implements RtCheckRunListener {
  public void setUp(RtChecker checker) {
    System.out.println(checker.title() + " (" + checker.level() + "). " + checker.description());
  }

  public void before(RtCheck check) {
    System.out.print("  " + check.name() + " (" + check.priority() + "): ");
  }

  public void after(RtCheck check, RtCheckResult checkResult) {
    System.out.println(checkResult.name() + " | " + check.resolveInstruction());
  }

  public void tearDown(RtChecker checker) {
    System.out.println("-----------------------------------");
  }

  public void error(RtChecker checker, RtCheck check, Exception ex) {
    System.out.println(ex.getMessage());
  }
}
