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

import javax.annotation.Nullable;

/**
 * Check result bean
 *
 * @author dsaponenko
 */
public class RtCheckResult {

  /**
   * Check result name
   */
  public enum Name {
    ACCEPT, UNSTABLE, FAILED, SKIPPED, INACTIVE
  }

  /**
   * Accept result constant
   */
  public static final RtCheckResult ACCEPT = accept("");
  /**
   * Unstable result constant
   */
  public static final RtCheckResult UNSTABLE = unstable("");
  /**
   * Failed result constant
   */
  public static final RtCheckResult FAILED = failed("");
  /**
   * Skipped result constant
   */
  public static final RtCheckResult SKIPPED = skipped("");
  /**
   * Inactive result constant
   */
  public static final RtCheckResult INACTIVE = inactive("");

  private Name name;
  private String message;

  /**
   * Constructor
   *
   * @param name Check result name
   * @param message Check result message
   */
  protected RtCheckResult(Name name, String message) {
    this.name = name;
    this.message = message;
  }

  /**
   * Return accept check result with message
   *
   * @param message Message
   * @return Check result
   */
  public static RtCheckResult accept(String message) {
    return new RtCheckResult(Name.ACCEPT, message);
  }

  /**
   * Return unstable check result with message
   *
   * @param message Message
   * @return Check result
   */
  public static RtCheckResult unstable(String message) {
    return new RtCheckResult(Name.UNSTABLE, message);
  }

  /**
   * Return failed check result with message
   *
   * @param message Message
   * @return Check result
   */
  public static RtCheckResult failed(String message) {
    return new RtCheckResult(Name.FAILED, message);
  }

  /**
   * Return skipped check result with message
   *
   * @param message Message
   * @return Check result
   */
  public static RtCheckResult skipped(String message) {
    return new RtCheckResult(Name.SKIPPED, message);
  }

  /**
   * Return inactive check result with message
   *
   * @param message Message
   * @return Check result
   */
  public static RtCheckResult inactive(String message) {
    return new RtCheckResult(Name.INACTIVE, message);
  }

  /**
   * Return Check result name
   *
   * @return Check result name
   */
  public Name getName() {
    return name;
  }

  /**
   * Return check result message
   *
   * @return Message
   */
  @Nullable
  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RtCheckResult)) return false;

    RtCheckResult result = (RtCheckResult) o;

    return name == result.name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
