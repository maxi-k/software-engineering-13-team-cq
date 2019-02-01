package com.bmw.fd.apimock.boundary.exposing.vehiclestate;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reports")
@org.springframework.web.bind.annotation.CrossOrigin
public class ReportMockController {

    private final RestMock serviceReports =  RestMock.create("data/reports")
            .withUUIDGenerator()
            .withInternalFields("id", "type", "carPark", "fleets")
            .build();

    @GetMapping("/summary")
    public ResponseEntity<ObjectNode> getSummary(
            @RequestParam(value = "car_park", required = false) String carPark,
            @RequestParam(value = "fleets", required = false) List<String> fleets) {
        return getReport("summary", carPark, fleets);
    }

    @GetMapping("/service")
    public ResponseEntity<ObjectNode> getServiceReport(
            @RequestParam(value = "car_park", required = false) String carPark,
            @RequestParam(value = "fleets", required = false) List<String> fleets) {
        return getReport("service", carPark, fleets);
    }

    private ResponseEntity<ObjectNode> getReport(String type, String carPark, List<String> fleets) {
        return serviceReports.getFirst(report -> {
            Set<String> reportFleets = new HashSet<>();
            report.get("fleets").forEach(f -> reportFleets.add(f.textValue()));
            return type.equals(report.get("type").textValue()) &&
                    ((fleets != null && reportFleets.containsAll(fleets)) || (carPark != null && carPark.equals(report.get("carPark").textValue())));
        });
    }
}
