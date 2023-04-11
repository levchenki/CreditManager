package com.uqii.creditmanager.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PassportDTO {

  private Long id;

  @NotNull(message = "Укажите серию паспорта")
  @Pattern(regexp = "\\d{4}", message = "Серия паспорта состоит из 4 цифр")
  private String series;

  @NotNull(message = "Укажите номер паспорта")
  @Pattern(regexp = "\\d{6}", message = "Номер паспорта состоит из 6 цифр")
  private String number;

  @NotNull(message = "Укажите код организации")
  @Pattern(regexp = "\\d{6}", message = "Код организации состоит из 6 цифр")
  private String divisionCode;

  @Length(min = 1, max = 255, message = "Длина поля, кем выдан паспорт, должна быть от 1 до 255 символов ")
  @NotNull(message = "Укажите, кем выдан паспорт")
  private String issuedBy;

  @NotNull(message = "Укажите дату выдачи паспорта")
  @PastOrPresent(message = "Дата выдачи паспорта не может быть в будущем")
  private LocalDate issuedDate;

  @Length(min = 1, max = 255, message = "Длина адреса прописки должна быть от 1 до 255 символов")
  @NotNull(message = "Укажите адрес прописки")
  private String registrationAddress;
}
