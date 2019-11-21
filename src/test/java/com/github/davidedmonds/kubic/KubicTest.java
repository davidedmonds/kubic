package com.github.davidedmonds.kubic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.davidedmonds.kubic.KubicTestHelper.listPods;
import static org.assertj.core.api.Assertions.assertThat;

class KubicTest {
    @Test
    @DisplayName("Kubic can start a pod")
    void startAPod() {
        Environment environment = new Environment("test");

        new Kubic().startEnvironment(environment);

        // assert
        assertThat(listPods()).contains(environment);
    }
}
