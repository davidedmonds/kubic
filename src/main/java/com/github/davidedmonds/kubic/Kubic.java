package com.github.davidedmonds.kubic;

import com.github.davidedmonds.kubic.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kubic {
    private static final Logger log = LoggerFactory.getLogger(Kubic.class);
    private final KubernetesClient client;

    public Kubic(KubernetesClient client) {
        this.client = client;
    }

    public void startEnvironment(Environment environment) throws Exception {
        log.info("Starting {}", environment);
        client.launchPods(environment);
        log.info("{} started", environment);
    }
}
