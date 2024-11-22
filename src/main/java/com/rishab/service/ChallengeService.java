package com.rishab.service;

import com.rishab.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class ChallengeService {
    private static final Logger log = LoggerFactory.getLogger(ChallengeService.class);
    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String candidateId;

    @Autowired
    public ChallengeService(@Value("${challenge.base.url}") String baseUrl, @Value("${challenge.candidate.id}") String candidateId) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        this.candidateId = candidateId;
    }

    /**
     * This method fetches the map from the API
     * * It returns the map
     * * If the map is empty, it logs that the map is empty and returns an empty map
     * *
     * * @return Map : Map fetched from the API
     */
    public Map getMap() {
        try {
            Map map = restTemplate.getForObject("/map/" + candidateId + "/goal", Map.class);
            if (map != null) map.printGoal();
            else log.info("Map is empty");
            return map;
        } catch (RestClientException e) {
            log.info("Error while fetching map : " + e.getMessage());
        }
        return new Map();
    }

    /**
     * This method maps the Megaverse
     * * It reads the goal from the map and posts the CelestialObjects to the API
     */
    public void mapTheMegaverse() {
        Map map = getMap();
        String[][] goal = map.getGoal();
        int row = goal.length;
        int col = goal[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (goal[i][j].equals("POLYANET")) {
                    Polyanet polyanet = new Polyanet(i, j, candidateId);
                    postCelestialObject(polyanet, "polyanets");
                } else if (goal[i][j].contains("COMETH")) {
                    String[] entity = goal[i][j].split("_");
                    String direction = entity[0].toLowerCase();
                    Cometh cometh = new Cometh(i, j, direction, candidateId);
                    postCelestialObject(cometh, "comeths");
                } else if (goal[i][j].contains("SOLOON")) {
                    String[] entity = goal[i][j].split("_");
                    String color = entity[0].toLowerCase();
                    Soloon soloon = new Soloon(i, j, candidateId, color);
                    postCelestialObject(soloon, "soloons");
                } else {
                }
            }
        }
    }

    /**
     * Post the CelestialObject to the API
     * * if the API returns 429, wait for 5s and retry
     * * if still 429, log the error and exits
     * *
     * * @param celestialObject : CelestialObject to post
     * * @param path            : API path to post the CelestialObject
     */
    public void postCelestialObject(CelestialOobject celestialObject, String path) {
        String body = celestialObject.writeAsJson();
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        try {
            ResponseEntity<Void> response = restTemplate.exchange(path, HttpMethod.POST, request, Void.class);
            logApiResponse(response);
        } catch (RestClientException e) {
            if (e.getMessage().contains("429")) {
                log.info("We got rate limited, lets wait for 5s and retry: " + e.getMessage());
                try {
                    Thread.sleep(5000);
                    restTemplate.exchange(path, HttpMethod.POST, request, Void.class);
                } catch (InterruptedException ex) {
                    log.error("Someone closed our application while waiting for rate limit: " + ex.getMessage());
                } catch (RestClientException ex) {
                    log.error("Guess 5seconds is too short, increase wait time further or rerun the request " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Log the response of the API call
     * *
     * * @param response : ResponseEntity of the API call
     */
    private void logApiResponse(ResponseEntity<Void> response) {
        if (response.getStatusCode().is2xxSuccessful()) log.info("CelestialObject posted successfully");
        else if (response.getStatusCode().is4xxClientError())
            log.error("Invalid request : " + response.getStatusCode() + " : " + response.getBody());
        else log.error("Error while posting CelestialObject: " + response.getStatusCode() + " : " + response.getBody());
    }

    /**
     * Delete object
     * @param deleteRequest
     * @return
     */

    public String deleteCelestialObject(DeleteRequest deleteRequest) {
        String body = deleteRequest.getCelestialOobject().writeAsJson();
        String type = deleteRequest.getCelestialObjectType();
        String path = "";
        if (type.equals("polyanets")) path = "/polyanets";
        else if (type.equals("comeths")) path = "/comeths";
        else if (type.equals("soloons")) path = "/soloons";
        else {
            log.error("Invalid CelestialObjectType");
            return "Invalid CelestialObjectType";
        }
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        try {
            ResponseEntity<Void> response = restTemplate.exchange(path, HttpMethod.DELETE, request, Void.class);
            logApiResponse(response);
        } catch (RestClientException e) {
            log.info("Error while deleting CelestialObject : " + e.getMessage());
        }
        return "Success";
    }
}