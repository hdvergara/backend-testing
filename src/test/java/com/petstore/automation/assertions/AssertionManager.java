package com.petstore.automation.assertions;

import io.qameta.allure.Allure;
import org.assertj.core.api.SoftAssertions;

/**
 * Soft assertions con AssertJ; los fallos se propagan a JUnit y Allure.
 */
public final class AssertionManager {

    private final SoftAssertions softAssertions = new SoftAssertions();
    private static final String MESSAGE = "La condición evaluada no coincide con el valor esperado";

    public void softAssertTrue(boolean condition) {
        softAssertions.assertThat(condition).as(MESSAGE).isTrue();
    }

    public void assertAllSoftAssertions() {
        try {
            softAssertions.assertAll();
        } catch (AssertionError e) {
            Allure.addAttachment("Fallos en soft assertions", "text/plain", e.getMessage(), ".txt");
            throw e;
        }
    }
}
