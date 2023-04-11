import {IClient} from "../../utils/interfaces";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

const SClientsTable = styled.div`
  padding: 1rem 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`

const SClientRow = styled.p`
  color: #242424;
  font-weight: 600;
  display: flex;
  align-items: center;
  padding: 1rem;
  border-radius: 1rem;
  background-color: #bde2ff;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #83c4ec;
  }

  span {
    margin-right: 1rem;
  }
`

export const ClientsTable = (props: { clients: IClient[] }) => {
  return (
      <SClientsTable>
        {
          props.clients.map(client => (
              <ClientRow key={client.id} {...client}/>
          ))
        }
      </SClientsTable>
  )
}

const ClientRow = (client: IClient) => {
  const navigate = useNavigate();

  return (
      <SClientRow onClick={() => navigate(`/clients/${client.id}`)}>
        <span>{client.lastname}</span>
        <span>{client.firstname}</span>
        <span>{client.patronymic}</span>
        <span>Телефон: {client.phone}</span>
        <span>Паспорт: {client.passport.series}{client.passport.number}</span>
      </SClientRow>
  );
}

