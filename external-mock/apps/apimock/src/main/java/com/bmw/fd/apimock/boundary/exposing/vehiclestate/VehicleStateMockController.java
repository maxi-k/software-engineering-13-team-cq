package com.bmw.fd.apimock.boundary.exposing.vehiclestate;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.bmw.fd.spring.api.CollectionResource;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleStateMockController {

    private final RestMock vehicles =  RestMock.create("data/vehicles")
            .withIdField("vin")
            .build();

    @GetMapping
    public ResponseEntity<CollectionResource<ObjectNode>> getVehicles(
            @RequestParam(value = "car_park", required = false) String carPark,
            @RequestParam(value = "fleets", required = false) List<String> fleets,
            @RequestParam(value = "updated_since", required = false) Instant updated_since) {
        return vehicles.get(
                v ->
                        ((fleets != null && fleets.contains(v.get("fleet").textValue()))
                          || (carPark != null && carPark.equals(v.get("carPark").textValue())))
                    && (updated_since == null || Instant.parse(v.get("lastDataTransfer").asText()).isAfter(updated_since))
                );
    }

    @GetMapping("{vin}")
    public ResponseEntity<ObjectNode> getVehicle(@PathVariable("vin") String vin) {
        return vehicles.get(vin);
    }

    @DeleteMapping("{vin}")
    public ResponseEntity<ObjectNode> deleteVehicle(@PathVariable("vin") String vin) {
        return vehicles.delete(vin);
    }

    @PostMapping
    public ResponseEntity<ObjectNode> createVehicle(@RequestBody ObjectNode vehicle) {
        return vehicles.post(vehicle);
    }


}
