package com.github.davidedmonds.kubic.client;

import com.github.davidedmonds.kubic.Environment;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KubeClient {
    private static final Logger log = LoggerFactory.getLogger(KubeClient.class);
    private final KubernetesClient client;

    public KubeClient() {
        client = new DefaultKubernetesClient();
    }

    public void launchPods(Environment environment) {
        log.debug("Launching pods for {}", environment);
        Container helloContainer = new ContainerBuilder()
                .withImage("gcr.io/hello-minikube-zero-install/hello-node")
                .build();
        String namespace = client.getNamespace();
        client.pods().inNamespace(namespace)
                .createNew()
                .withNewSpec()
                .withContainers(helloContainer)
                .endSpec()
                .done();
    }
}
