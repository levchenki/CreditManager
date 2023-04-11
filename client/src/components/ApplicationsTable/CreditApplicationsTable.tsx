import {CreditStatus, ICreditApplication} from "../../utils/interfaces";
import {formatCreditApplicationStatus, Status} from "../../utils/helpers";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import {getCreditContractByApplication} from "../../utils/api";

const SApplicationsTable = styled.div`
  padding: 1rem 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`

const SApplicationRow = styled.p<{ status: CreditStatus }>`
  color: #242424;
  font-weight: 600;
  display: flex;
  align-items: center;
  padding: 1rem;
  border-radius: 1rem;
  background-color: #bde2ff;
  cursor: ${props => props.status === CreditStatus.APPROVED ? 'pointer' : ''};
  transition: background-color 0.3s ease;


  &:hover {
    background-color: ${props => props.status == CreditStatus.APPROVED ? '#83c4ec' : '#bde2ff'};
  }

  span {
    margin-right: 1rem;
  }
`

export const CreditApplicationsTable = (props: { creditApplications: ICreditApplication[] }) => {
  return (
      <SApplicationsTable>
        {
          props.creditApplications.map(application => (
              <CreditApplicationRow key={application.id} {...application}/>
          ))
        }
      </SApplicationsTable>
  )
}

const CreditApplicationRow = (application: ICreditApplication) => {
  const navigate = useNavigate();

  const getCreditContractId = async (applicationId: number) => {
    const data = await getCreditContractByApplication(applicationId);
    return data.data.id;
  }

  const goToContractHandler = async (status: CreditStatus) => {
    if (status === CreditStatus.APPROVED) {
      const contractId = await getCreditContractId(application.id)
      navigate(`/contracts/${contractId}`)
    }
  }

  return (
      <SApplicationRow status={application.status}
                       onClick={() => goToContractHandler(application.status)}>
        <span>{application.client.lastname}</span>
        <span>{application.client.firstname}</span>
        <span>{application.client?.patronymic}</span>
        <span>Паспорт: {application.client.passport.series}{application.client.passport.number}</span>
        <span>Запрашиваемая сумма: {application.desiredMoney}₽</span>
        <Status
            status={application.status}>{formatCreditApplicationStatus(application.status)}</Status>
      </SApplicationRow>
  )
}
