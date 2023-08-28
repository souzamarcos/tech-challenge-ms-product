package com.fiap.burger.usecase.usecase;

import com.fiap.burger.entity.client.Client;
import com.fiap.burger.usecase.adapter.gateway.ClientGateway;
import com.fiap.burger.usecase.adapter.usecase.ClientUseCase;
import com.fiap.burger.usecase.misc.exception.ClientCpfAlreadyExistsException;
import com.fiap.burger.usecase.misc.exception.InvalidAttributeException;
import org.springframework.stereotype.Service;

import static com.fiap.burger.usecase.misc.validation.ValidationCPF.validateCPF;
import static com.fiap.burger.usecase.misc.validation.ValidationUtils.*;

@Service
public class DefaultClientUseCase implements ClientUseCase {

    public Client findById(ClientGateway repository, Long id) {return repository.findById(id);}

    public Client findByCpf(ClientGateway repository, String cpf) {return repository.findByCpf(cpf);}

    public Client insert(ClientGateway repository, Client client) {
        Client persistedClient = findByCpf(repository, client.getCpf());
        if(persistedClient != null) {
            throw new ClientCpfAlreadyExistsException();
        }
        validateClientToInsert(client);
        return repository.save(client);
    }

    private void validateClientToInsert(Client client) {
        if(client.getId() != null) throw new InvalidAttributeException("Client should not have defined id", "id");
        validateClient(client);
    }

    private void validateClient(Client client) {
        validateNotNull(client.getCpf(), "cpf");
        validateNotBlank(client.getCpf(), "cpf");
        validateCPF(client.getCpf());
        validateNotNull(client.getEmail(), "email");
        validateNotBlank(client.getEmail(), "email");
        validateEmailFormat(client.getEmail(), "email");
        validateNotNull(client.getName(), "name");
        validateNotBlank(client.getName(), "name");
    }
}