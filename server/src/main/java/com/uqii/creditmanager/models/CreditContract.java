package com.uqii.creditmanager.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_contracts")
public class CreditContract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "credit_contract_id", nullable = false)
  private Long id;

  @Min(30)
  @Max(365)
  @Column(name = "days_count", nullable = false)
  private int daysCount;

  @Column(name = "approved_money", nullable = false)
  private BigDecimal approvedMoney;

  @Column(name = "signature_date")
  private LocalDate signatureDate;

  @Column(name = "is_signed")
  private boolean isSigned;

  @OneToOne
  @JoinColumn(name = "credit_application_id")
  private CreditApplication creditApplication;
}
