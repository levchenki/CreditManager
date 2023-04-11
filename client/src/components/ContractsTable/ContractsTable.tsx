import {ICreditContract} from "../../utils/interfaces";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

const SClientsTable = styled.div`
  padding: 1rem 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`

const SContractRow = styled.p`
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

const Sign = styled.span<{ isSigned: boolean }>`
  font-weight: 700;
  color: ${props => props.isSigned ? 'green' : 'red'};
`

export const ContractsTable = (props: { contracts: ICreditContract[] }) => {
  return (
      <SClientsTable>
        {
          props.contracts.map(client => (
              <ClientRow key={client.id} {...client}/>
          ))
        }
      </SClientsTable>
  )
}

const ClientRow = (contract: ICreditContract) => {
  const navigate = useNavigate()
  const creditApplication = contract.creditApplication
  const client = creditApplication.client

  const signHandler = (isSigned: boolean) => {
    return isSigned ? "Подписан" : "Не подписан"
  }

  const goToContractHandler = (contractId: number) => {
    navigate(`/contracts/${contractId}`)
  }

  return (
      <SContractRow onClick={() => goToContractHandler(contract.id)}>
        <span>{client.lastname}</span>
        <span>{client.firstname}</span>
        <span>{client?.patronymic}</span>
        <span>Паспорт: {client.passport.series}{client.passport.number}</span>
        <span>Одобренная сумма: {contract.approvedMoney}₽</span>
        <span>Количество дней: {contract.daysCount}</span>
        <Sign isSigned={contract.signed}>{signHandler(contract.signed)}</Sign>
        {
            contract.signatureDate && <span>Дата подписи: {contract.signatureDate.toString()}</span>
        }
      </SContractRow>
  );
}

