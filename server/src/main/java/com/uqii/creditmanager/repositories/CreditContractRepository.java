package com.uqii.creditmanager.repositories;

import com.uqii.creditmanager.models.CreditContract;
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
public class CreditContractRepository {

  SessionFactory sessionFactory;

  @Transactional
  public CreditContract saveCreditContract(CreditContract contract) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    try (session) {
      session.save(contract);
      transaction.commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      transaction.rollback();
      throw e;
    }
    return contract;
  }

  @Transactional(readOnly = true)
  public List<CreditContract> getCreditContracts() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select cs from CreditContract cs", CreditContract.class)
        .getResultList();
  }

  @Transactional(readOnly = true)
  public List<CreditContract> getCreditContracts(Long clientId) {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery(
        "select cs from CreditContract cs where cs.creditApplication.client.id = :clientId",
        CreditContract.class).setParameter("clientId", clientId).getResultList();
  }

  @Transactional(readOnly = true)
  public Optional<CreditContract> getCreditContract(Long contractId) {
    Session session = sessionFactory.getCurrentSession();
    return session.byId(CreditContract.class).loadOptional(contractId);
  }

  public CreditContract updateCreditContract(CreditContract contract) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    try (session) {
      session.update(contract);
      transaction.commit();
    } catch (Exception e) {
      log.error(e.getMessage());
      transaction.rollback();
      throw e;
    }
    return contract;
  }
}
