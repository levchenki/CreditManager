import React, {useEffect, useState} from "react";
import {IClient} from "../../utils/interfaces";
import {getClients} from "../../utils/api";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import {ClientsTable} from "../../components/ClientsTable/ClientsTable";

const SRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`
const SearchRow = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;
  justify-content: center;
  padding: 1rem 0;
  & > div {
    display: flex;
    gap: 1rem;
    align-items: center;
  }
`

export const ClientsPage = () => {
  const [clients, setClients] = useState<IClient[]>([])

  const navigate = useNavigate();

  const [fullname, setFullname] = useState('')
  const [phone, setPhone] = useState('')
  const [passport, setPassport] = useState('')


  useEffect(() => {
    getClients().then(data => {
      if (data)
        setClients(data)
    });
  }, [])

  useEffect(() => {
    if (!fullname && !phone && !passport)
      navigate('/clients')
  }, [fullname, phone, passport])

  const searchClients = () => {
    let param = '';

    if (phone.length > 0) {
      param = `/?phone=${phone}`
    }
    if (passport.length > 0) {
      param = `/?passport=${passport}`
    }
    if (fullname.length > 0) {
      param = `/?name=${fullname}`
    }

    navigate(`/clients${param}`)

    getClients(param).then(data => {
      if (data) {
        setClients(data)
      }
    })
  }

  return (
      <>
        <SRow>
          <h2>Клиенты</h2>
          <button onClick={() => navigate('/clients/new')}>Добавить</button>
        </SRow>
        <SearchRow>
          <input placeholder='поиск по ФИО'
                   value={fullname}
                   onChange={event => {
                     setPhone('')
                     setPassport('')
                     setFullname(event.target.value)
                   }}/>

          <input placeholder='поиск по телефону'
                   value={phone}
                   onChange={event => {
                     setFullname('')
                     setPassport('')
                     setPhone(event.target.value)
                   }}/>

          <input placeholder='поиск по паспорту'
                   value={passport}
                   onChange={event => {
                     setFullname('')
                     setPhone('')
                     setPassport(event.target.value)
                   }}/>

          <button onClick={searchClients}>Поиск</button>
        </SearchRow>
        {
          clients.length ? <ClientsTable clients={clients}/>
              : <h3>Ничего не найдено</h3>
        }
      </>
  )
}