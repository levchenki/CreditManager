package com.uqii.creditmanager.dto;

import com.uqii.creditmanager.models.MaritalStatus;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ClientDTO {

  private Long id;

  @Length(min = 1, max = 100, message = "Длина имени должна быть от 1 до 100 символов")
  @NotNull(message = "Укажите имя")
  private String firstname;

  @Length(min = 1, max = 100, message = "Длина фамилии должна быть от 1 до 100 символов")
  @NotNull(message = "Укажите фамилию")
  private String lastname;

  @Length(max = 100, message = "Максимальная длина отчества - 100 символов")
  private String patronymic;

  @NotNull(message = "Укажите семейное положение")
  private MaritalStatus maritalStatus;

  @NotNull(message = "Укажите телефон")
  @Pattern(regexp = "7\\d{10}", message = "Телефон должен быть в формате 7ХХХХХХХХХХ")
  private String phone;

  @Valid
  @NotNull(message = "Укажите паспорт")
  private PassportDTO passport;

  @Valid
  @NotNull(message = "Укажите сведения о работе")
  private List<EmploymentInfoDTO> employmentInfo;
}
