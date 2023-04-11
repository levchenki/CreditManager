import {formatMaritalStatus} from "../../utils/helpers";
import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";
import {IClient} from "../../utils/interfaces";
import {createApplication, getClientById} from "../../utils/api";
import {toast} from "react-toastify";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`

const SForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 60%;
  min-width: 300px;
  margin-top: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 1rem;

  & > label {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    margin-bottom: 1rem;
    font-size: 18px;
  }
`

export const NewCreditApplication = () => {
  const [desiredMoney, setDesiredMoney] = useState<string>('');
  const navigate = useNavigate();
  const params = useParams<{ id: string }>()
  const [client, setClient] = useState<IClient>()

  const createCreditApplication = () => {
    if (!desiredMoney.match('^\\d+([.]\\d{2})?$'))
      toast.error("Сумма желаемого кредита должна быть числом", {
        autoClose: 5000,
        position: 'bottom-right',
      })
    else {
      if (client) {
        const formData = new FormData()
        formData.append("desiredMoney", desiredMoney.toString());
        formData.append("client[id]", client?.id.toString())

        createApplication(formData).then(data => {
          toast.info("Заявка добавлена", {
            autoClose: 5000,
            position: 'bottom-right',
          })
          navigate(`/clients/${client.id}`)
        }).catch(e => {
          const errors: string = e.response.data.message;
          errors.split('.').filter(error => error.length).map(error => {
            toast.error(error, {
              autoClose: 5000,
              position: 'bottom-right',
            })
          })
        })
      }
    }
  }

  useEffect(() => {
    if (params.id)
      getClientById(parseInt(params.id))
      .then(data => setClient(data))
  }, [])

  const passport = client?.passport

  return client && passport
      ? (
          <Container>
            <h2>Оформление заявки на кредит</h2>
            <SForm>
              <label>
                Фамилия: &nbsp;
                <p>{client.lastname}</p>
              </label>
              <label>
                Имя: &nbsp;
                <p>{client.firstname}</p>
              </label>
              {
                  client?.patronymic && <label>
                    Отчество: &nbsp;
                    <p>{client?.patronymic}</p>
                  </label>
              }
              <label>
                Телефон: &nbsp;
                <p>{client.phone}</p>
              </label>
              <label>
                Семейное положение: &nbsp;
                <p>{formatMaritalStatus(client.maritalStatus)}</p>
              </label>
              <label>
                Серия и номер паспорта: &nbsp;
                <p>{passport.series} {passport.number}</p>
              </label>
              <label>
                Код подразделения: &nbsp;
                <p>{passport.divisionCode}</p>
              </label>
              <label>
                Паспорт выдан: &nbsp;
                <p>{passport.issuedBy}</p>
              </label>
              <label>
                Дата выдачи паспорта: &nbsp;
                <p>{passport.issuedDate}</p>
              </label>
              <label>
                Адрес регистрации: &nbsp;
                <p>{passport.registrationAddress}</p>
              </label>
              {
                client.employmentInfo.map(info => (
                    <React.Fragment key={info.id}>
                      <label>Организация:&nbsp;
                        {info.organizationName}
                      </label>
                      <label>Должность:&nbsp;
                        {info.position}
                      </label>
                      <label>Период:&nbsp;
                        {info.startDate} - {info.endDate ? info.endDate : 'настоящее время'}
                      </label>
                    </React.Fragment>
                ))
              }
              <label>
                Сумма желаемого кредита: &nbsp;
                <input type="text" value={desiredMoney}
                       onChange={event => setDesiredMoney(event.target.value)}/>
              </label>
            </SForm>
            <button onClick={createCreditApplication}>Отправить</button>
          </Container>
      )
      : null
}