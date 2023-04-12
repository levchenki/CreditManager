import styled from "styled-components";
import {useState} from "react";
import {MaritalStatus} from "../../utils/interfaces";
import {formatDate, formatMaritalStatus, getMaritalStatus} from "../../utils/helpers";
import {postClient} from "../../utils/api";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

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
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 1rem;
    font-size: 18px;
  }
`

export const NewClientPage = () => {
  const navigate = useNavigate();

  const [firstname, setFirstname] = useState<string>('')
  const [lastname, setLastname] = useState<string>('')
  const [patronymic, setPatronymic] = useState<string>('')
  const [phone, setPhone] = useState<string>('')
  const [maritalStatus, setMaritalStatus] = useState<string>(MaritalStatus.MARRIED)

  const [series, setSeries] = useState<string>('')
  const [number, setNumber] = useState<string>('')
  const [divisionCode, setDivisionCode] = useState<string>('')
  const [issuedBy, setIssuedBy] = useState<string>('')
  const [issuedDate, setIssuedDate] = useState<string>(formatDate(new Date()))
  const [registrationAddress, setRegistrationAddress] = useState<string>('')

  const [organizationName, setOrganizationName] = useState<string>('')
  const [position, setPosition] = useState<string>('')
  const [startDate, setStartDate] = useState<string>(formatDate(new Date()))
  const [isActiveWork, setIsActiveWork] = useState(true)
  const [endDate, setEndDate] = useState<string>(formatDate(new Date()))


  const createClient = () => {
    const formData = new FormData()
    formData.append("firstname", firstname)
    formData.append("lastname", lastname)
    formData.append("patronymic", patronymic)
    formData.append("phone", phone)
    formData.append("maritalStatus", maritalStatus)

    formData.append('passport[series]', series.toString())
    formData.append('passport[number]', number.toString())
    formData.append('passport[divisionCode]', divisionCode.toString())
    formData.append('passport[issuedBy]', issuedBy)
    formData.append('passport[issuedDate]', issuedDate)
    formData.append('passport[registrationAddress]', registrationAddress)

    formData.append('employmentInfo[0][organizationName]', organizationName)
    formData.append('employmentInfo[0][position]', position)
    formData.append('employmentInfo[0][startDate]', startDate)
    if (!isActiveWork)
      formData.append('employmentInfo[0][endDate]', endDate)

    postClient(formData).then(data => {
      const id = data.data;
      navigate(`/clients/${id}`)
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

  return (
      <Container>
        <h2>Создание нового клиента</h2>
        <SForm>
          <label>
            Фамилия:&nbsp;
            <input type="text" name="lastname"
                   value={lastname}
                   onChange={event => setLastname(event.target.value)}/>
          </label>
          <label>
            Имя:&nbsp;
            <input type="text"
                   name="firstname"
                   value={firstname}
                   onChange={event => setFirstname(event.target.value)}/>
          </label>
          <label>
            Отчество:&nbsp;
            <input type="text" name="patronymic"
                   value={patronymic}
                   onChange={event => setPatronymic(event.target.value)}/>
          </label>
          <label>
            Телефон:&nbsp;
            <input type="tel" name="phone"
                   value={phone}
                   onChange={event => setPhone(event.target.value)}
            />
          </label>
          <label>
            Семейное положение:&nbsp;
            <select value={maritalStatus} onChange={event => setMaritalStatus(event.target.value)}>
              {
                getMaritalStatus().map(status => {
                  return (
                      <option key={status} value={status} label={formatMaritalStatus(status)}/>
                  )
                })
              }
            </select>
          </label>
          <label>
            Серия и номер паспорта:&nbsp;
            <input type="text" name="series"
                   value={series}
                   onChange={event => setSeries(event.target.value)}
            />
            <input type="text" name="number"
                   value={number}
                   onChange={event => setNumber(event.target.value)}
            />
          </label>
          <label>
            Код подразделения:&nbsp;
            <input type="text" name="divisionCode"
                   value={divisionCode}
                   onChange={event => setDivisionCode(event.target.value)}
            />
          </label>
          <label>
            Паспорт выдан:&nbsp;
            <input type="text" name="issuedBy"
                   value={issuedBy}
                   onChange={event => setIssuedBy(event.target.value)}
            />
          </label>
          <label>
            Дата выдачи:&nbsp;
            <input type="date" name="issuedDate"
                   defaultValue={issuedDate}
                   onChange={event => setIssuedDate(event.target.value)}
            />
          </label>
          <label>
            Адрес регистрации:&nbsp;
            <input type="text" name="registrationAddress"
                   value={registrationAddress}
                   onChange={event => setRegistrationAddress(event.target.value)}
            />
          </label>
          <label>
            Название организации:&nbsp;
            <input type="text" name="organizationName"
                   value={organizationName}
                   onChange={event => setOrganizationName(event.target.value)}
            />
          </label>
          <label>
            Должность:&nbsp;
            <input type="text" name="position"
                   value={position}
                   onChange={event => setPosition(event.target.value)}
            />
          </label>
          <label>
            Дата начала работы:&nbsp;
            <input type="date" defaultValue={startDate}
                   name="startDate"
                   onChange={event => setStartDate(event.target.value)}
            />
          </label>
          <label>
            Текущая работа:&nbsp;
            <input type="checkbox" checked={isActiveWork}
                   onChange={event => setIsActiveWork(event.target.checked)}/>
          </label>
          <label>
            Дата конца работы:&nbsp;
            <input disabled={isActiveWork} type="date"
                   defaultValue={endDate}
                   name="endDate"
                   onChange={event => setEndDate(event.target.value)}
            />
          </label>
        </SForm>
        <button onClick={createClient}>Создать</button>
      </Container>
  )
}