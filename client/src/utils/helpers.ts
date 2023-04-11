import {CreditStatus, MaritalStatus} from "./interfaces";
import styled from "styled-components";

export const formatMaritalStatus = (status: MaritalStatus): string => {
  switch (status) {
    case MaritalStatus.MARRIED:
      return "Женат/Замужем";
    case MaritalStatus.NOT_MARRIED:
      return "Не женат/Не замужем";
  }
};

export const getMaritalStatus = (): MaritalStatus[] => {
  return [MaritalStatus.MARRIED, MaritalStatus.NOT_MARRIED]
}

export const formatDate = (date: Date): string => {
  return date.toISOString().substring(0, 10)
}

export const formatCreditApplicationStatus = (status: CreditStatus): string => {
  switch (status) {
    case CreditStatus.APPROVED:
      return "Заявка одобрена";
    case CreditStatus.DENIED:
      return "Заявка отклонена";
    case CreditStatus.WAITING:
      return "Заявка отправлена"
  }
}

export const Status = styled.span<{ status: CreditStatus }>`
  font-weight: 700;
  color: ${props => props.status === CreditStatus.DENIED ? 'red' : 'green'};
`