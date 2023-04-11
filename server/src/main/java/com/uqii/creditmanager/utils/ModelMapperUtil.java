package com.uqii.creditmanager.utils;

import com.uqii.creditmanager.dto.ClientDTO;
import com.uqii.creditmanager.dto.CreditApplicationDTO;
import com.uqii.creditmanager.dto.CreditContractDTO;
import com.uqii.creditmanager.dto.EmploymentInfoDTO;
import com.uqii.creditmanager.models.Client;
import com.uqii.creditmanager.models.CreditApplication;
import com.uqii.creditmanager.models.CreditContract;
import com.uqii.creditmanager.models.EmploymentInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelMapperUtil {

  ModelMapper modelMapper;

  public Client convertDTOToClient(ClientDTO clientDTO) {
    return modelMapper.map(clientDTO, Client.class);
  }

  public ClientDTO convertClientToDTO(Client client) {
    List<EmploymentInfo> employmentInfo = client.getEmploymentInfo();
    List<EmploymentInfoDTO> employmentInfoDTOS = employmentInfo.stream()
        .map(info -> modelMapper.map(info, EmploymentInfoDTO.class)).collect(Collectors.toList());

    ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
    clientDTO.setEmploymentInfo(employmentInfoDTOS);
    return clientDTO;
  }

  public CreditApplication convertDTOToApplication(CreditApplicationDTO applicationDTO) {
    return modelMapper.map(applicationDTO, CreditApplication.class);
  }

  public CreditApplicationDTO convertApplicationToDTO(CreditApplication creditApplication) {
    return modelMapper.map(creditApplication, CreditApplicationDTO.class);
  }

  public CreditContract convertDTOToContract(CreditContractDTO contractDTO) {
    return modelMapper.map(contractDTO, CreditContract.class);
  }

  public CreditContractDTO convertContractToDTO(CreditContract creditContract) {
    return modelMapper.map(creditContract, CreditContractDTO.class);
  }
}
