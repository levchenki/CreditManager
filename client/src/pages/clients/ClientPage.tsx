import React, {useEffect, useState} from "react";
import {IClient, ICreditApplication} from "../../utils/interfaces";
import {useNavigate, useParams} from "react-router-dom";
import {getClientById, getCreditApplicationsByClient} from "../../utils/api";
import styled from "styled-components";
import {formatCreditApplicationStatus, formatMaritalStatus, Status} from "../../utils/helpers";
import {toast} from "react-toastify";

const StyledForm = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
`

const SDivBlock = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 1rem;
  gap: 1rem;
`

const InnerBlock = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`

export const ClientPage = () => {
  const navigate = useNavigate();
  const params = useParams<{ id: string }>()
  const [client, setClient] = useState<IClient>()
  const [applications, setApplications] = useState<ICreditApplication[]>()

  useEffect(() => {
    if (params.id)
      getClientById(parseInt(params.id))
      .then(data => setClient(data))
      .catch(e => {
        const error: string = e.response.data.message;
        toast.error(error, {
          autoClose: 5000,
          position: 'bottom-right',
        })
      })
  }, [])

  useEffect(()=>{
    if (client) {
      getCreditApplicationsByClient(client.id)
      .then(data => setApplications(data))
      .catch(e => {
        const error: string = e.response.data.message;
        toast.error(error, {
          autoClose: 5000,
          position: 'bottom-right',
        })
      })
    }
  },[client])

  const passport = client?.passport

  return client && passport
      ? (
          <>
            <h2>{client.lastname} {client.firstname} {client?.patronymic}</h2>
            <StyledForm>
              <SDivBlock>
                <p>Телефон: {client.phone}</p>
                <p>Семейное положение: {formatMaritalStatus(client.maritalStatus)}</p>
                <InnerBlock>
                  <h3>Паспортные данные</h3>
                  <p>Серия и номер: {passport.series} {passport.number}</p>
                  <p>Код подразделения: {passport.divisionCode}</p>
                  <p>Выдан: {passport.issuedBy}</p>
                  <p>Дата выдачи: {passport.issuedDate}</p>
                  <p>Адрес регистрации: {passport.registrationAddress}</p>
                </InnerBlock>
                <InnerBlock>
                  <h3>Сведения о работе</h3>
                  {
                    client.employmentInfo.map(info => (
                        <React.Fragment key={info.id}>
                          <p>Организация: {info.organizationName}</p>
                          <p>Должность: {info.position}</p>
                          <p>Период
                            работы: {info.startDate} - {info.endDate ? info.endDate : 'настоящее время'}</p>
                        </React.Fragment>
                    ))
                  }
                </InnerBlock>
              </SDivBlock>
              <SDivBlock>
                {
                  applications?.length
                      ? (<>
                            <h2>Заявки на кредит:</h2>
                            {
                              applications.map(application => (
                                  <p key={application.id}>Запрашиваемая
                                    сумма: {application.desiredMoney}₽ <Status
                                        status={application.status}>{formatCreditApplicationStatus(application.status)}</Status>
                                  </p>
                              ))
                            }
                          </>
                      )
                      : <h2>У клиента ещё нет заявок на кредит</h2>
                }
                <button onClick={() => navigate(`/clients/${params.id}/new_application`)}>Оформить
                  заявку на кредит
                </button>
              </SDivBlock>
            </StyledForm>
          </>
      )
      : null
}