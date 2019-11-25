package com.github.davidedmonds.kubic;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KubicTestHelper {
    private static final Logger log = LoggerFactory.getLogger(KubicTestHelper.class);
    private static ApiClient apiClient;

    public static List<Environment> listPods() throws ApiException, IOException {
        CoreV1Api api = new CoreV1Api(apiClient());
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        return list.getItems().stream()
                .map(KubicTestHelper::podToEnvironment)
                .collect(Collectors.toList());
    }

    private static ApiClient apiClient() throws IOException {
        if (apiClient == null) {
            log.debug("Establishing connection to Kubernetes");
            apiClient = Config.defaultClient();
            log.debug("Kubernetes connected");
        }
        return apiClient;
    }

    private static Environment podToEnvironment(V1Pod v1Pod) {
        return new Environment(v1Pod.getMetadata().getName());
    }
}
