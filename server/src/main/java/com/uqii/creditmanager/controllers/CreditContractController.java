package com.uqii.creditmanager.controllers;

import com.uqii.creditmanager.dto.CreditContractDTO;
import com.uqii.creditmanager.models.CreditContract;
import com.uqii.creditmanager.services.CreditContractService;
import com.uqii.creditmanager.utils.ModelMapperUtil;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/contracts")
@RestController
@AllArgsConstructor
public class CreditContractController {

  CreditContractService contractService;
  ModelMapperUtil modelMapperUtil;

  @GetMapping("/all")
  public List<CreditContractDTO> getContracts(@RequestParam(required = false) Long clientId) {
    List<CreditContract> creditContracts =
        clientId == null ? contractService.getCreditContracts()
            : contractService.getCreditContracts(clientId);

    return creditContracts.stream().map(modelMapperUtil::convertContractToDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreditContractDTO> getContract(@PathVariable Long id) {
    Optional<CreditContract> optionalCreditContract = contractService.getCreditContract(id);

    if (optionalCreditContract.isPresent()) {
      CreditContract contract = optionalCreditContract.get();
      CreditContractDTO creditContractDTO = modelMapperUtil.convertContractToDTO(contract);
      return ResponseEntity.ok(creditContractDTO);
    } else {
      throw new NoSuchElementException("Кредитный договор не найден");
    }
  }

  @GetMapping()
  public ResponseEntity<CreditContractDTO> getContractByApplicationId(@RequestParam(name = "application") Long applicationId) {
    Optional<CreditContract> optionalCreditContract = contractService.getCreditContractByApplicationId(applicationId);

    if (optionalCreditContract.isPresent()) {
      CreditContract contract = optionalCreditContract.get();
      CreditContractDTO creditContractDTO = modelMapperUtil.convertContractToDTO(contract);
      return ResponseEntity.ok(creditContractDTO);
    } else {
      throw new NoSuchElementException("Кредитный договор не найден");
    }
  }
  @PostMapping("/{id}")
  public ResponseEntity<CreditContractDTO> signContract(@PathVariable long id) {
    CreditContract signedContract = contractService.signContract(id);
    return ResponseEntity.ok(modelMapperUtil.convertContractToDTO(signedContract));
  }
}
