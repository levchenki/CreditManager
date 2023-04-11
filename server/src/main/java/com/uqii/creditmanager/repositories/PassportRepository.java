package com.uqii.creditmanager.repositories;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class PassportRepository {

  private final SessionFactory sessionFactory;

  @Transactional(readOnly = true)
  public boolean isExists(String series, String number) {
    Session session = sessionFactory.getCurrentSession();
    Long result = session.createQuery(
            "select count(*) from Passport p where p.series = :series and p.number = :number",
            Long.class)
        .setParameter("series", series)
        .setParameter("number", number)
        .getSingleResult();
    return result > 0;
  }
}
