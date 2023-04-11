package com.uqii.creditmanager.controllers;

import com.uqii.creditmanager.dto.ClientDTO;
import com.uqii.creditmanager.models.Client;
import com.uqii.creditmanager.services.ClientService;
import com.uqii.creditmanager.utils.ModelMapperUtil;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/clients")
@RestController
@AllArgsConstructor
public class ClientController {

  private final ClientService clientService;
  private final ModelMapperUtil modelMapperUtil;

  @GetMapping("/all")
  public List<ClientDTO> getClients(@RequestParam(name = "phone", required = false) String phone,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "passport", required = false) String passport) {
    List<Client> clients;
    if (phone != null) {
      clients = clientService.getClientsByPhone(phone);
    } else if (name != null) {
      clients = clientService.getClientsByName(name);
    } else if (passport != null) {
      clients = clientService.getClientsByPassport(passport);
    } else {
      clients = clientService.getClients();
    }

    return clients.stream().map(modelMapperUtil::convertClientToDTO).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
    Optional<Client> optionalClient = clientService.getClient(id);

    if (optionalClient.isPresent()) {
      Client client = optionalClient.get();

      ClientDTO clientDTO = modelMapperUtil.convertClientToDTO(client);
      return ResponseEntity.ok(clientDTO);
    } else {
      throw new NoSuchElementException("Клиент не найден");
    }
  }

  @PostMapping
  public ResponseEntity<Long> createClient(@Valid @RequestBody ClientDTO clientDTO,
      BindingResult bindingResult) {
    Client client = modelMapperUtil.convertDTOToClient(clientDTO);

    if (bindingResult.hasErrors()) {
      StringBuilder textError = new StringBuilder();
      for (ObjectError error : bindingResult.getAllErrors()) {
        textError.append(error.getDefaultMessage()).append(".");
      }
      throw new IllegalArgumentException(textError.toString());
    }

    Long createdClientId = clientService.createClient(client);
    return ResponseEntity.ok(createdClientId);
  }
}
