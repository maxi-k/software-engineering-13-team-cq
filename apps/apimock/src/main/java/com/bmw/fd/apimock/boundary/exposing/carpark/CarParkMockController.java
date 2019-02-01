package com.bmw.fd.apimock.boundary.exposing.carpark;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.bmw.fd.spring.api.CollectionResource;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/car-parks")
@org.springframework.web.bind.annotation.CrossOrigin
public class CarParkMockController {

    private final RestMock carParks = RestMock.create("data/car-parks")
            .withUUIDGenerator()
            .withResponseFilter(this::withFleets)
            .build();

    @Autowired
    private FleetMockController fleetMockController;

    @GetMapping
    public ResponseEntity<CollectionResource<ObjectNode>> getAllCarParks(Authentication principal) {
        return carParks.get(cp -> {
            if (principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_FD_ALL") || role.equals("ROLE_FD_SERVER")))
                return true;
            Set<String> admins = new HashSet<>();
            cp.get("admins").forEach(f -> admins.add(f.textValue()));
            return admins.contains(principal.getName());
        });
    }

    @GetMapping("/{carParkId}")
    public ResponseEntity<ObjectNode> getCarPark(@PathVariable("carParkId") String carParkId) {
        return carParks.get(carParkId);
    }

    private ObjectNode withFleets(ObjectNode carPark) {
        String carParkId = carParks.store().getId(carPark);
        List<ObjectNode> fleets = new ArrayList<>();
        fleetMockController.getFleets(carParkId).getBody().getItems().forEach(fleets::add);
        ObjectNode carParkWithFleets = carPark.deepCopy();
        carParkWithFleets.putArray("fleets").addAll(fleets);
        return carParkWithFleets;
    }

    @PostMapping
    public ResponseEntity<ObjectNode> createCarPark(@RequestBody ObjectNode carPark) {
        return carParks.post(carPark);
    }

    @PutMapping(value = "/{carParkId}")
    public ResponseEntity<ObjectNode> updateCarPark(@PathVariable("carParkId") String carParkId, @RequestBody ObjectNode carPark) {
        return carParks.put(carParkId, carPark);
    }

    @DeleteMapping(value = "/{carParkId}")
    public ResponseEntity<ObjectNode> deleteCarPark(@PathVariable("carParkId") String carParkId)  {
        return carParks.delete(carParkId);
    }
}
