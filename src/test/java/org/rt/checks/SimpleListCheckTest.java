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
import org.rt.checks.impl.runners.RtListCheckRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dsaponenko
 */
@RtChecker(level = RtChecker.Level.CORE,
    title = "Simple list test", description = "Simple list test description")
public class SimpleListCheckTest {

  @RtCheck(priority = RtCheck.Priority.LOWEST, name = "Test 1", resolveInstruction = "See test1")
  RtCheckResult test1() {
    return RtCheckResult.UNSTABLE;
  }

  @Test
  public void runChecks() throws Exception {
    final int[] setupCounter = {0};
    Set<Class<?>> checkers = new HashSet<Class<?>>();
    checkers.add(SimpleCheckTest.class);
    checkers.add(SimpleListCheckTest.class);
    new RtListCheckRunner(checkers).
        run(new BaseTestRtCheckRunListener() {
          @Override
          public void setUp(RtChecker checker) {
            super.setUp(checker);
            setupCounter[0]++;
          }
        });

    Assert.assertEquals(2, setupCounter[0]);
  }
}
