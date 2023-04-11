package com.uqii.creditmanager.models;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_applications")
public class CreditApplication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "credit_application_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "desired_money", nullable = false)
  private BigDecimal desiredMoney;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CreditApplicationStatus status;

  @OneToOne(mappedBy = "creditApplication")
  private CreditContract creditContract;
}
