package com.uqii.creditmanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditContractDTO {

  private Long id;

  @NotNull(message = "Укажите количество срок кредита")
  private int daysCount;

  @Digits(integer = 19, fraction = 2)
  @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
  @NotNull(message = "Укажите одобренную сумму кредита")
  private BigDecimal approvedMoney;

  @PastOrPresent(message = "Дата подписания должна быть валидной")
  @NotNull(message = "Укажите дату подписания")
  private LocalDate signatureDate;

  @NotNull(message = "Укажите статус подписи")
  private boolean isSigned;

  private CreditApplicationDTO creditApplication;
}
