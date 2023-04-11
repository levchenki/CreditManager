package com.uqii.creditmanager.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
@NoArgsConstructor
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "client_id", nullable = false)
  private Long id;

  @Column(name = "firstname", nullable = false, length = 100)
  private String firstname;

  @Column(name = "lastname", nullable = false, length = 100)
  private String lastname;

  @Column(name = "patronymic", length = 100)
  private String patronymic;

  @Column(name = "marital_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private MaritalStatus maritalStatus;

  @Pattern(regexp = "7\\d{10}")
  @Column(name = "phone", unique = true, nullable = false, length = 11)
  private String phone;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "passport_id", nullable = false)
  private Passport passport;

  @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<EmploymentInfo> employmentInfo;

  @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
  private List<CreditApplication> creditApplications;
}
