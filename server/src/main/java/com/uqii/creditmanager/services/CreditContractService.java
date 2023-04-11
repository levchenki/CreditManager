package com.uqii.creditmanager.services;

import com.uqii.creditmanager.models.CreditApplication;
import com.uqii.creditmanager.models.CreditContract;
import com.uqii.creditmanager.repositories.CreditApplicationRepository;
import com.uqii.creditmanager.repositories.CreditContractRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditContractService {

  CreditContractRepository creditContractRepository;
  CreditApplicationRepository creditApplicationRepository;

  public Optional<CreditContract> getCreditContract(Long contractId) {
    return creditContractRepository.getCreditContract(contractId);
  }

  public Optional<CreditContract> getCreditContractByApplicationId(Long applicationId) {
    CreditApplication creditApplication = creditApplicationRepository.getCreditApplication(
        applicationId).orElseThrow(() -> new NoSuchElementException("Кредитная заявка не найдена"));

    return Optional.of(creditApplication.getCreditContract());
  }

  public void createContract(CreditApplication creditApplication) {
    CreditContract contract = new CreditContract();
    contract.setCreditApplication(creditApplication);
    contract.setDaysCount(generateDays());
    contract.setApprovedMoney(creditApplication.getDesiredMoney());
    contract.setSigned(false);
    creditContractRepository.saveCreditContract(contract);
  }

  private int generateDays() {
    Random random = new Random();
    int from = 30;
    int to = 365;
    return random.nextInt(to - from) + from;
  }

  public List<CreditContract> getCreditContracts() {
    return creditContractRepository.getCreditContracts();
  }

  public List<CreditContract> getCreditContracts(Long clientId) {
    return creditContractRepository.getCreditContracts(clientId);
  }

  public CreditContract signContract(Long contractId) {
    CreditContract contract = getCreditContract(contractId).orElseThrow(
        () -> new NoSuchElementException("Кредитный договор c id " + contractId + " не найден"));

    if (contract.isSigned()) {
      throw new IllegalArgumentException("Кредитный договор c id " + contractId + " уже подписан");
    }
    LocalDate date = LocalDate.now();
    contract.setSigned(true);
    contract.setSignatureDate(date);
    return creditContractRepository.updateCreditContract(contract);
  }
}
