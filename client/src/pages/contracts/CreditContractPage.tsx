import {useLocation, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ICreditContract} from "../../utils/interfaces";
import {getCreditContractById, signCreditContract} from "../../utils/api";
import styled from "styled-components";
import {toast} from "react-toastify";
import {formatMaritalStatus} from "../../utils/helpers";

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

const Status = styled.p<{ isSigned: boolean }>`
  font-weight: 700;
  color: ${props => props.isSigned ? 'green' : 'red'};
`

export const CreditContractPage = () => {
  const [contract, setContract] = useState<ICreditContract>()
  const params = useParams<{ id: string }>()
  const location = useLocation();

  useEffect(() => {
    if (params.id) {
      getCreditContractById(+params.id).then(data => {
        setContract(data.data)
      })
      .catch(e => {
        const error: string = e.response.data.message;
        toast.error(error, {
          autoClose: 5000,
          position: 'bottom-right',
        })
      })
    }
  }, [])

  const signHandler = (contractId: number) => {
    signCreditContract(contractId).then(data => {
      toast.info("Контракт подписан", {
        autoClose: 5000,
        position: 'bottom-right',
      })
      setContract(data.data)
    })
    .catch(e => {
      const errors: string = e.response.data.message;
      errors.split('.').filter(error => error.length).map(error => {
        toast.error(error, {
          autoClose: 5000,
          position: 'bottom-right',
        })
      })
    })
  }

  const application = contract?.creditApplication;
  const client = application?.client
  return contract && application && client ? <>
        <h2>Кредитный договор</h2>
        <StyledForm>
          <SDivBlock>
            <p>Фамилия: {client.lastname}</p>
            <p>Имя: {client.firstname}</p>
            {
                client.patronymic ?? <p>Отчество: {client?.patronymic}</p>
            }
            <p>Телефон: {client.phone}</p>
            <p>Семейное
              положение: {formatMaritalStatus(client.maritalStatus)}</p>
            <p>Серия и номер паспорта: {client.passport.series} {client.passport.number}</p>
            <p>Код подразделения: {client.passport.divisionCode}</p>
            <p>Паспорт выдан: {client.passport.issuedBy}</p>
            <p>Дата выдачи: {client.passport.issuedDate}</p>
            <p>Адрес регистрации: {client.passport.registrationAddress}</p>
            {
              client.employmentInfo.map(info => (
                  <React.Fragment key={info.id}>
                    <p>Организация: {info.organizationName}</p>
                    <p>Должность: {info.position}</p>
                    <p>Период
                      работы: {info.startDate} - {info.endDate ? info.endDate : 'настоящее время'}</p>
                  </React.Fragment>
              ))
            }          </SDivBlock>

          <SDivBlock>
            <p>Одобренная сумма кредита: {contract.approvedMoney}₽</p>
            <p>Количество дней: {contract.daysCount}</p>
            <Status
                isSigned={contract.signed}>{contract.signed ? 'Подписан' : 'Не подписан'}</Status>
            {
              contract.signed && contract.signatureDate
                  ? 'Дата подписи: ' + contract.signatureDate
                  : <button onClick={() => signHandler(contract.id)}>Подписать</button>}
          </SDivBlock>
        </StyledForm>
      </>
      : null
}