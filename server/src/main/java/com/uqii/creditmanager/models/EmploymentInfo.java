package com.uqii.creditmanager.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employment_info")
@NoArgsConstructor
public class EmploymentInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employment_info_id", nullable = false)
  private Long id;

  @Column(name = "organization_name", nullable = false)
  private String organizationName;

  @Column(name = "position", nullable = false, length = 100)
  private String position;

  @PastOrPresent
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @PastOrPresent
  @Column(name = "end_date")
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @PrePersist
  @PreUpdate
  public void checkDates() {
    if (endDate != null && startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("startDate should be before endDate");
    }
  }
}
