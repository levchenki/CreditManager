package com.uqii.creditmanager.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class EmploymentInfoDTO {

  private Long id;

  @Length(min = 1, max = 255, message = "Длина названия организации должна быть от 1 до 100 символов")
  @NotNull(message = "Введите название организации")
  private String organizationName;

  @Length(min = 1, max = 100, message = "Длина должности должна быть от 1 до 100 символов")
  @NotNull(message = "Введите должность")
  private String position;

  @NotNull(message = "Введите дату начала работы")
  @PastOrPresent(message = "Дата начала работы должна быть раньше сегодняшней")
  private LocalDate startDate;

  @PastOrPresent(message = "Дата конца работы должна быть раньше сегодняшней")
  private LocalDate endDate;
}
