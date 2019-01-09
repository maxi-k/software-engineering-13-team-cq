package com.bmw.fd.apimock.boundary.exposing.carpark;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.bmw.fd.spring.api.CollectionResource;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/car-parks/{carParkId}/fleets")
public class FleetMockController {

    private final RestMock fleets = RestMock.create("data/fleets")
            .withUUIDGenerator()
            .withInternalFields("carPark")
            .build();


    @GetMapping
    public ResponseEntity<CollectionResource<ObjectNode>> getFleets(@PathVariable("carParkId") String carParkId) {
        return fleets.get(belongsToCarPark(carParkId));
    }

    @GetMapping(value = "/{fleetId}")
    public ResponseEntity<ObjectNode> getFleet(@PathVariable("fleetId") String fleetId) {
        return fleets.get(fleetId);
    }

    @PostMapping
    public ResponseEntity<ObjectNode> addFleet(@PathVariable("carParkId") String carParkId, @RequestBody ObjectNode fleet) {
        fleet.put("carPark", carParkId);
        return fleets.post(fleet);
    }

    @PutMapping(value = "/{fleetId}")
    public ResponseEntity<ObjectNode> updateFleet(@PathVariable("carParkId") String carParkId, @PathVariable("fleetId") String fleetId, @RequestBody ObjectNode fleet) {
        fleet.put("carPark", carParkId);
        return fleets.put(fleetId, fleet);
    }

    @DeleteMapping(value = "/{fleetId}")
    public ResponseEntity<ObjectNode> deleteFleet(@PathVariable("fleetId") String fleetId) {
        return fleets.delete(fleetId);
    }

    private static Predicate<ObjectNode> belongsToCarPark(@PathVariable("carParkId") String carParkId) {
        return ObjectNode -> ObjectNode.get("carPark").textValue().equals(carParkId);
    }

}
