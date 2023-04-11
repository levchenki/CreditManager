import {
  CreditApplicationsTable
} from "../../components/ApplicationsTable/CreditApplicationsTable";
import React, {useEffect, useState} from "react";
import {getCreditApplications} from "../../utils/api";
import {ICreditApplication} from "../../utils/interfaces";
import styled from "styled-components";

const SRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`

export const CreditApplicationsPage = () => {
  const [creditApplications, setCreditApplications] = useState<ICreditApplication[]>([])

  useEffect(() => {
    getCreditApplications().then(data => {
          if (data)
            setCreditApplications(data)
        }
    )
  }, [])

  return (
      <>
        <SRow>
          <h2>Заявки на кредит</h2>
        </SRow>
        <CreditApplicationsTable creditApplications={creditApplications}/>
      </>
  )
}