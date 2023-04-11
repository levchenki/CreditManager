import styled from "styled-components";
import React, {useEffect, useState} from "react";
import {ICreditContract} from "../../utils/interfaces";
import {getCreditContracts} from "../../utils/api";
import {ContractsTable} from "../../components/ContractsTable/ContractsTable";

const SRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`


export const CreditContractsPage = () => {
  const [contracts, setContracts] = useState<ICreditContract[]>([])

  useEffect(() => {
    getCreditContracts().then(data => {
      setContracts(data.data)
    })
    .catch(e => {
      console.log(e)
    })
  }, [])

  return contracts && (
      <>
        <SRow>
          <h2>Кредитные договоры</h2>
        </SRow>
        <ContractsTable contracts={contracts}/>
      </>
  )
}