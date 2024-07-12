package com.example.pj_webshop.controllers.moderation;

import com.example.pj_webshop.entities.authentication.Client;
import com.example.pj_webshop.services.moderation.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable int id) {
        Optional<Client> clientOpt = clientService.findById(id);
        return clientOpt.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Client>> findAllClients() {
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/find/order/{id}")
    public ResponseEntity<Client> findClientByOrderId(@PathVariable int id) {
        Optional<Client> clientOpt = clientService.findClientByOrderId(id);
        return clientOpt.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/find/email")
    public ResponseEntity<Client> findClientByEmail(@RequestParam String email) {
        Optional<Client> clientOpt = clientService.findByEmail(email);
        return clientOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/find/phone")
    public ResponseEntity<Client> findClientByPhone(@RequestParam String phone) {
        Optional<Client> clientOpt = clientService.findByPhone(phone);
        return clientOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addNewClient(@RequestBody Client client) {
        Optional<Client> clientOpt = clientService.addNew(client);
        return clientOpt.map(savedClient -> ResponseEntity.status(HttpStatus.OK).body(savedClient))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable int id) {
        boolean isDeleted = clientService.deleteById(id);
        if (!isDeleted)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody Client updatedClient) {
        Optional<Client> clientOpt = clientService.updateClient(id, updatedClient);
        return clientOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
