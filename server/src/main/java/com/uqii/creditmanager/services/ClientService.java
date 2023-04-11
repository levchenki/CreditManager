package com.uqii.creditmanager.services;

import com.uqii.creditmanager.models.Client;
import com.uqii.creditmanager.models.EmploymentInfo;
import com.uqii.creditmanager.models.Passport;
import com.uqii.creditmanager.repositories.ClientRepository;
import com.uqii.creditmanager.repositories.PassportRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;
  private final PassportRepository passportRepository;

  public List<Client> getClients() {
    return clientRepository.getClients();
  }

  public Optional<Client> getClient(Long clientId) {
    return clientRepository.getClient(clientId);
  }

  public Long createClient(Client client) {

    validateClient(client);
    Passport passport = client.getPassport();
    List<EmploymentInfo> employmentInfo = client.getEmploymentInfo();

    passport.setClient(client);
    client.setPassport(passport);
    for (EmploymentInfo info : employmentInfo) {
      info.setClient(client);
    }

    return clientRepository.createClient(client).getId();
  }

  private void validateClient(Client client) {
    List<String> errors = new ArrayList<>();

    Passport passport = client.getPassport();
    List<EmploymentInfo> employmentInfo = client.getEmploymentInfo();

    String series = passport.getSeries();
    String number = passport.getNumber();

    if (passportRepository.isExists(series, number)) {
      errors.add("Паспорт с такими серией и номером уже существует.");
    }

    if (isExists(client.getPhone())) {
      errors.add("Клиент с таким номером телефона уже существует.");
    }

    for (EmploymentInfo info : employmentInfo) {
      LocalDate startDate = info.getStartDate();
      LocalDate endDate = info.getEndDate();
      if (endDate != null && startDate.isAfter(endDate)) {
        errors.add("Дата начала должна быть раньше даты конца.");
      }
    }

    if (!errors.isEmpty()) {
      String errorMessage = formErrorMessage(errors);
      throw new IllegalArgumentException(errorMessage);
    }
  }

  private String formErrorMessage(List<String> errors) {
    StringBuilder text = new StringBuilder();
    for (String e : errors) {
      text.append(e);
    }
    return text.toString();
  }

  private boolean isExists(String phone) {
    return !clientRepository.getClientsByPhone(phone).isEmpty();
  }

  public List<Client> getClientsByPhone(String phone) {
    return clientRepository.getClientsByPhone(phone);
  }

  public List<Client> getClientsByName(String name) {
    String[] splitted = name.toLowerCase().split("\\s+");
    String lastname = splitted[0];
    String firstname = splitted.length > 1 ? splitted[1] : "";
    String patronymic = splitted.length > 2 ? splitted[2] : "";

    return clientRepository.getClientsByName(lastname, firstname, patronymic);
  }

  public List<Client> getClientsByPassport(String passport) {
    return clientRepository.getClientsByPassport(passport);
  }
}
