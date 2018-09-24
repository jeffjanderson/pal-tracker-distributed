package io.pivotal.pal.tracker.allocations;



import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.ConcurrentHashMap;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String endpoint;
    private ConcurrentHashMap cMap = new ConcurrentHashMap();


    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {

        ProjectInfo pInfo = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);
        cMap.put(projectId, pInfo);

        return pInfo;
    }
}
