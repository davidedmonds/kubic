package com.github.davidedmonds.kubic.client;

import com.github.davidedmonds.kubic.Environment;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
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
        log.info("Creating {} namespace", environment);
        client.namespaces().createOrReplaceWithNew()
                .withNewMetadata()
                .withName(environment.getName())
                .endMetadata()
                .done();

        Container helloContainer = new ContainerBuilder()
                .withImage("gcr.io/hello-minikube-zero-install/hello-node")
                .withName("hello-container")
                .build();
        log.info("Launching pods for {}", environment);
        client.pods().inNamespace(environment.getName())
                .createNew()
                .withNewMetadata()
                .withName(environment.getName())
                .endMetadata()
                .withNewSpec()
                .withContainers(helloContainer)
                .endSpec()
                .done();
    }
}
