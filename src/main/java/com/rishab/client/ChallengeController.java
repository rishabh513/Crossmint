package com.rishab.client;


import com.rishab.model.DeleteRequest;
import com.rishab.model.Map;
import com.rishab.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChallengeController {
    private ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * This method fetches the map from the API     *     * @return Map : Map fetched from the API
     */
    @GetMapping("/look_into_the_telescope")
    public Map getMap() {
        return challengeService.getMap();
    }

    /**
     * This method maps the Megaverse
     * * It reads the goal from the map and posts the CelestialObjects to the API
     */

    @GetMapping("/draw_megaverse")
    public void challenge() {
        challengeService.mapTheMegaverse();
    }

    /**
     * This method deletes the Celestial Object     *     * @param deleteRequest : DeleteRequest : Request to delete the Celestial Object     * @return ResponseEntity<String> : Response Entity with the result of the deletion
     */
    @DeleteMapping("/delete_celestial_object")
    public ResponseEntity<String> deleteCelestialObject(@RequestBody DeleteRequest deleteRequest) {
        String result = challengeService.deleteCelestialObject(deleteRequest);
        if (result.equals("Success")) {
            return ResponseEntity.ok("Celestial Object deleted successfully");
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}