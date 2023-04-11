package com.uqii.creditmanager.repositories;

import com.uqii.creditmanager.models.CreditApplication;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Repository
@AllArgsConstructor
public class CreditApplicationRepository {

  private final SessionFactory sessionFactory;

  @Transactional(readOnly = true)
  public Optional<CreditApplication> getCreditApplication(Long applicationId) {
    Session session = sessionFactory.getCurrentSession();
    return session.byId(CreditApplication.class).loadOptional(applicationId);
  }

  @Transactional(readOnly = true)
  public List<CreditApplication> getCreditApplications() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select ca from CreditApplication ca", CreditApplication.class)
        .getResultList();
  }

  @Transactional(readOnly = true)
  public List<CreditApplication> getCreditApplications(Long clientId) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("from CreditApplication ca where ca.client.id = :clientId",
        CreditApplication.class).setParameter("clientId", clientId).getResultList();
  }

  @Transactional
  public CreditApplication createCreditApplication(CreditApplication creditApplication) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    try (session) {
      session.save(creditApplication);
      transaction.commit();
      log.info("CREATED");
    } catch (Exception e) {
      log.error(e.getMessage());
      transaction.rollback();
      throw e;
    }
    return creditApplication;
  }

  @Transactional
  public void updateCreditApplication(CreditApplication creditApplication) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    try (session) {
      session.update(creditApplication);
      transaction.commit();
    } catch (Exception e) {
      log.error(e.getMessage());
      transaction.rollback();
      throw e;
    }
  }
}
