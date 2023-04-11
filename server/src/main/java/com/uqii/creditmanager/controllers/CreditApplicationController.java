package com.uqii.creditmanager.controllers;

import com.uqii.creditmanager.dto.CreditApplicationDTO;
import com.uqii.creditmanager.models.CreditApplication;
import com.uqii.creditmanager.services.CreditApplicationService;
import com.uqii.creditmanager.services.CreditFacade;
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

@RequestMapping("/api/applications")
@RestController
@AllArgsConstructor
public class CreditApplicationController {

  ModelMapperUtil modelMapperUtil;
  CreditApplicationService creditApplicationService;
  CreditFacade creditFacade;

  @GetMapping("/all")
  public List<CreditApplicationDTO> getApplications(@RequestParam(required = false) Long clientId) {
    List<CreditApplication> creditApplications =
        clientId == null ? creditApplicationService.getCreditApplications()
            : creditApplicationService.getCreditApplications(clientId);

    return creditApplications.stream()
        .map(modelMapperUtil::convertApplicationToDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreditApplicationDTO> getApplication(@PathVariable Long id) {
    Optional<CreditApplication> optionalCreditApplication = creditApplicationService.getCreditApplication(
        id);
    if (optionalCreditApplication.isPresent()) {
      CreditApplication creditApplication = optionalCreditApplication.get();
      CreditApplicationDTO creditApplicationDTO = modelMapperUtil.convertApplicationToDTO(
          creditApplication);
      return ResponseEntity.ok(creditApplicationDTO);
    } else {
      throw new NoSuchElementException("Кредитная заявка не найдена");
    }
  }

  @PostMapping
  public ResponseEntity<Long> createApplication(
      @RequestBody @Valid CreditApplicationDTO creditApplicationDTO,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      StringBuilder textError = new StringBuilder();
      for (ObjectError error : bindingResult.getAllErrors()) {
        textError.append(error.getDefaultMessage()).append(".");
      }
      throw new IllegalArgumentException(textError.toString());
    }

    CreditApplication creditApplication = modelMapperUtil.convertDTOToApplication(
        creditApplicationDTO);
    Long creditApplicationId = creditFacade.create(creditApplication);
    return ResponseEntity.ok(creditApplicationId);
  }
}
