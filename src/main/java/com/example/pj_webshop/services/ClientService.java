package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.Client;
import com.example.pj_webshop.entities.ClientOrder;
import com.example.pj_webshop.repositories.ClientOrderRepository;
import com.example.pj_webshop.repositories.ClientRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    public void setClientOrderRepository(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    public Optional<Client> addNew(@NotNull Client client) {
        if (this.findByPhone(client.getPhone()).isPresent() || this.findByEmail(client.getEmail()).isPresent())
            return Optional.empty();

        this.clientRepository.save(client);
        return Optional.of(client);
    }

    public boolean deleteById(int id) {
        Optional<Client> client = this.clientRepository.findById(id);

        if (client.isEmpty())
            return false;

        this.clientRepository.deleteById(id);
        return true;
    }

    public Optional<Client> findClientByOrderId(int orderId) {
        List<ClientOrder> clientOrders = clientOrderRepository.findByOrderId(orderId);

        if (clientOrders.isEmpty()) {
            return Optional.empty();
        }

        List<Integer> clientIds = clientOrders.stream()
                .map(ClientOrder::getClientId)
                .toList();

        return clientIds.stream()
                .map(clientRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    public Optional<Client> updateClient(int id, Client updatedClient) {
        Optional<Client> existingClientOpt = this.clientRepository.findById(id);

        if (existingClientOpt.isEmpty())
            return Optional.empty();

        Client existingClient = existingClientOpt.get();

        existingClient.setClientId(updatedClient.getClientId());
        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setLastName(updatedClient.getLastName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhone(updatedClient.getPhone());

        Client savedClient = this.clientRepository.save(existingClient);

        return Optional.of(savedClient);
    }
}
