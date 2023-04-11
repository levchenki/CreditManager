package com.uqii.creditmanager.repositories;

import com.uqii.creditmanager.models.Client;
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
public class ClientRepository {

  private final SessionFactory sessionFactory;

  @Transactional(readOnly = true)
  public Optional<Client> getClient(Long clientId) {
    Session session = sessionFactory.getCurrentSession();

    return session.byId(Client.class).loadOptional(clientId);
  }

  @Transactional(readOnly = true)
  public List<Client> getClients() {
    Session session = sessionFactory.getCurrentSession();

    return session.createQuery("select c from Client c", Client.class)
        .getResultList();
  }

  @Transactional
  public Client createClient(Client client) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();

    try (session) {
      session.save(client);
      transaction.commit();
    } catch (Exception e) {
      log.error(e.getMessage());
      transaction.rollback();
      throw e;
    }
    return client;
  }

  @Transactional(readOnly = true)
  public List<Client> getClientsByPhone(String phone) {
    Session session = sessionFactory.getCurrentSession();

    return session.createQuery("from Client where phone like :phone", Client.class)
        .setParameter("phone", "%" + phone + "%").getResultList();
  }

  @Transactional(readOnly = true)
  public List<Client> getClientsByName(String lastname, String firstname, String patronymic) {
    Session session = sessionFactory.getCurrentSession();

    return session.createQuery(
            "from Client c where lower(c.lastname) like :lastname and lower(c.firstname) like :firstname and lower(c.patronymic) like :patronymic",
            Client.class)
        .setParameter("lastname", "%" + lastname + "%")
        .setParameter("firstname", "%" + firstname + "%")
        .setParameter("patronymic", "%" + patronymic + "%").getResultList();
  }

  public List<Client> getClientsByPassport(String passport) {
    Session session = sessionFactory.getCurrentSession();

    return session.createQuery(
            "from Client c where concat(c.passport.series,'',c.passport.number) like :passport",
            Client.class)
        .setParameter("passport", "%" + passport + "%")
        .getResultList();
  }
}
