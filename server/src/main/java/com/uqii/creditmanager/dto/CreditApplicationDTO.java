package com.uqii.creditmanager.dto;

import com.uqii.creditmanager.models.CreditApplicationStatus;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditApplicationDTO {

  private Long id;

  @Digits(integer = 19, fraction = 2)
  @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
  @NotNull(message = "Введите сумму желаемого кредита")
  private BigDecimal desiredMoney;

  private CreditApplicationStatus status;

  @NotNull(message = "Укажите клиента")
  private ClientDTO client;
}
