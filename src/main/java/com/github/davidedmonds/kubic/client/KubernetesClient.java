package com.github.davidedmonds.kubic.client;

import com.github.davidedmonds.kubic.Environment;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Namespace;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KubernetesClient {
    private static final Logger log = LoggerFactory.getLogger(KubernetesClient.class);
    private CoreV1Api api = null;

    public void launchPods(Environment environment) throws IOException, ApiException {
        log.debug("Creating {} namespace", environment.getName());
        V1Namespace namespace = new V1Namespace();
        api().createNamespace(namespace, false, null, null);

        log.debug("Launching pods for {}", environment);
        V1Pod pod = new V1Pod();
        api().createNamespacedPod(environment.getName(), pod, false, null, null);
    }

    private CoreV1Api api() throws IOException {
        if (api == null) {
            api = new CoreV1Api(Config.defaultClient());
        }
        return api;
    }
}
