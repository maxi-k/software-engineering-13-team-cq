package com.bmw.fd.apimock.boundary.exposing.carpark;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.bmw.fd.apimock.boundary.validation.OpenApiSchemaLocator;
import com.bmw.fd.spring.api.CollectionResource;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/car-parks/{carParkId}/vehicles")
@org.springframework.web.bind.annotation.CrossOrigin
public class VehicleMockController {

    private final RestMock vehicles;

    public VehicleMockController() { //OpenApiSchemaLocator schemaLocator) {
        //Schema schema = schemaLocator.locateSchema("/api/car-parks/{carParkId}/vehicles/{vin}");
        vehicles = RestMock.create("data/vehicles")
                .withIdField("vin")
        //        .withSchema(schema)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionResource<ObjectNode>> getVehicles(@PathVariable("carParkId") String carParkId,
                                                                   @RequestParam(value = "fleet", required = false) String fleetId) {
        return vehicles.get(v -> (fleetId != null && fleetId.equals(v.get("fleet").textValue()))
                || (carParkId != null && carParkId.equals(v.get("carPark").textValue())));
    }

    @GetMapping(produces = "text/csv")
    public void getVehiclesCsv(@PathVariable("carParkId") String carParkId,
                               @RequestParam(value = "fleet", required = false) String fleet,
                               HttpServletResponse response) throws IOException {
        throw new UnsupportedOperationException("CSV not yet supported");
    }

    @GetMapping(value = "/template", produces = "text/csv")
    public void getVehicleTemplateCSV(HttpServletResponse response) throws IOException {
        throw new UnsupportedOperationException("CSV not yet supported");
    }

    @GetMapping(value = "/{vin}")
    public ResponseEntity getVehicle(@PathVariable("vin") String vin) {
        return vehicles.get(vin);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> createVehicle(@PathVariable("carParkId") String carParkId, @RequestBody ObjectNode vehicle) {
        vehicle.put("carPark", carParkId);
        vehicle.put("created", Instant.now().toString());
        return vehicles.post(vehicle);
    }

    @PutMapping(value = "/{vin}")
    public ResponseEntity<ObjectNode> updateVehicle(@PathVariable("carParkId") String carParkId, @PathVariable("vin") String vin,  @RequestBody ObjectNode vehicle) {
        vehicle.put("carPark", carParkId);
        vehicle.put("modified", Instant.now().toString());
        return vehicles.put(vin, vehicle);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadVehicles(@PathVariable("carParkId") String carParkId, @RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        throw new UnsupportedOperationException("CSV not yet supported");
    }


    @DeleteMapping(value = "/{vin}")
    public ResponseEntity deleteVehicle(@PathVariable("vin") String vin) {
        return vehicles.delete(vin);
    }

    @PatchMapping(consumes = "application/json-patch+json")
    public ResponseEntity patchVehicles(@PathVariable("carParkId") UUID carParkId, @RequestBody ArrayNode operations) {
        throw new UnsupportedOperationException("PATCH not yet supported");
    }

}
