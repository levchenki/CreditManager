package com.uqii.creditmanager.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passport", uniqueConstraints = @UniqueConstraint(columnNames = {"series", "number"}))
@NoArgsConstructor
public class Passport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "passport_id", nullable = false)
  private Long id;

  @Pattern(regexp = "\\d{4}")
  @Column(name = "series", nullable = false, length = 4)
  private String series;

  @Pattern(regexp = "\\d{6}")
  @Column(name = "number", nullable = false, length = 6)
  private String number;

  @Pattern(regexp = "\\d{6}")
  @Column(name = "division_code", nullable = false, length = 6)
  private String divisionCode;

  @Column(name = "issued_by", nullable = false)
  private String issuedBy;

  @PastOrPresent
  @Column(name = "issued_date", nullable = false)
  private LocalDate issuedDate;

  @Column(name = "registration_address", nullable = false)
  private String registrationAddress;

  @OneToOne(mappedBy = "passport", optional = false)
  private Client client;
}
