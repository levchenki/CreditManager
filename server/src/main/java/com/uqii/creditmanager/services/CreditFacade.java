package com.uqii.creditmanager.services;

import com.uqii.creditmanager.models.CreditApplication;
import com.uqii.creditmanager.models.CreditApplicationStatus;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditFacade {

  CreditApplicationService creditApplicationService;
  CreditContractService creditContractService;

  public Long create(CreditApplication creditApplication) {
    CreditApplication created = creditApplicationService.createCreditApplication(
        creditApplication);
    makeDecision(created);
    return created.getId();
  }

  public void makeDecision(CreditApplication creditApplication) {
    Random random = new Random();
    int randomValue = random.nextInt(10);

    if (randomValue >= 5) {
      creditApplication.setStatus(CreditApplicationStatus.APPROVED);
      creditApplicationService.updateCreditApplication(creditApplication);
      creditContractService.createContract(creditApplication);
    } else {
      creditApplication.setStatus(CreditApplicationStatus.DENIED);
      creditApplicationService.updateCreditApplication(creditApplication);
    }
  }
}
