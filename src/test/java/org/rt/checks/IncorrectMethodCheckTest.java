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

import org.junit.Assert;
import org.junit.Test;
import org.rt.checks.annotation.RtCheck;
import org.rt.checks.annotation.RtChecker;
import org.rt.checks.impl.runners.RtConcreteCheckRunner;

/**
 * @author dsaponenko
 */
@RtChecker(level = RtChecker.Level.ANOTHER,
    title = "Incorrect method test", description = "Incorrect method description")
public class IncorrectMethodCheckTest {

  @RtCheck(priority = RtCheck.Priority.CRITICAL, name = "Test Incorrect method", resolveInstruction = "Change method signature")
  void testIncorrectMethod() {
  }

  @Test
  public void runChecks() throws Exception {
    new RtConcreteCheckRunner(IncorrectMethodCheckTest.class).run(new BaseTestRtCheckRunListener() {
      @Override
      public void error(RtChecker checker, RtCheck check, Exception ex) {
        super.error(checker, check, ex);
        if (check != null) {
          Assert.assertEquals(RtCheck.Priority.CRITICAL, check.priority());
        }
      }
    });
  }
}
