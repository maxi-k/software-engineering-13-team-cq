package com.bmw.fd.apimock.boundary.exposing.account;

import com.bmw.fd.apimock.boundary.util.RestMock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountMockController {

    private final RestMock accounts = RestMock.create("data/accounts")
            .withUUIDGenerator()
            .build();

    @GetMapping()
    public ResponseEntity getAccounts() {
        return accounts.getAll();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity getAccount(@PathVariable("accountId") String accountId) {
        return accounts.get(accountId);
    }
}
