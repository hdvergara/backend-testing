package backend.utilities;

import com.aventstack.extentreports.ExtentTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

public class AssertionManager {

    private final ExtentTest extentTest;
    private final SoftAssertions softAssertions;
    private static final String MESSAGE_FAIL_HARD_ASSERTION = "The condition to be compared was not matched";

    /**
     * Construction method
     *
     * @param extentTest Parameter for configuring and sending messages to the report during assertion
     */
    public AssertionManager(ExtentTest extentTest) {
        this.extentTest = extentTest;
        this.softAssertions = new SoftAssertions();
    }

    /**
     * Method that performs a hard assertion of type true
     *
     * @param condition Condition to be evaluated
     */
    public void hardAssertTrue(boolean condition) {
        try {
            Assertions.assertThat(condition)
                    .as(MESSAGE_FAIL_HARD_ASSERTION)
                    .isTrue();
        } catch (AssertionError error) {
            extentTest.fail(MESSAGE_FAIL_HARD_ASSERTION + "- Failed: " + error.getMessage());
        }
    }

    /**
     * Method that performs a hard assertion of type equals
     *
     * @param condition Condition to be evaluated
     * @param expected  Expected value at which the condition is evaluated
     */
    public void hardAssertEquals(Object condition, Object expected) {
        try {
            Assertions.assertThat(condition)
                    .as(MESSAGE_FAIL_HARD_ASSERTION)
                    .isEqualTo(expected);
        } catch (AssertionError error) {
            extentTest.fail(MESSAGE_FAIL_HARD_ASSERTION + "- Failed: " + error.getMessage());
        }
    }

    /**
     * Method that performs a Soft assertion of type equals
     *
     * @param condition Condition to be evaluated
     */
    public void softAssertTrue(boolean condition) {

        softAssertions.assertThat(condition)
                .as(MESSAGE_FAIL_HARD_ASSERTION)
                .isTrue();
    }

    /**
     * Method where all Soft assertions that failed are administered.
     */
    public void assertAllSoftAssertions() {

        try {
            softAssertions.assertAll();
        } catch (AssertionError e) {
            extentTest.fail("Soft assertions - Failed: " + e.getMessage());
            throw e;
        }
    }
}
