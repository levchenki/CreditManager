package com.uqii.creditmanager.services;

import com.uqii.creditmanager.models.CreditApplication;
import com.uqii.creditmanager.models.CreditApplicationStatus;
import com.uqii.creditmanager.repositories.CreditApplicationRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditApplicationService {

  private final CreditApplicationRepository creditApplicationRepository;

  public Optional<CreditApplication> getCreditApplication(Long applicationId) {
    return creditApplicationRepository.getCreditApplication(applicationId);
  }

  public List<CreditApplication> getCreditApplications() {
    return creditApplicationRepository.getCreditApplications();
  }

  public List<CreditApplication> getCreditApplications(Long clientId) {
    return creditApplicationRepository.getCreditApplications(clientId);
  }

  public CreditApplication createCreditApplication(CreditApplication creditApplication) {
    CreditApplication created = creditApplicationRepository.createCreditApplication(
        creditApplication);
    created.setStatus(CreditApplicationStatus.WAITING);
    return created;
  }

  public void updateCreditApplication(CreditApplication creditApplication) {
    creditApplicationRepository.updateCreditApplication(creditApplication);
  }
}
