import {useNavigate} from "react-router-dom";
import styled from "styled-components";

const SHeader = styled.header`
  display: flex;
  gap: 3rem;
  align-items: center;
  justify-content: center;
  padding: 2rem 0;
`

export const Header = () => {

  const navigate = useNavigate();

  return (
      <SHeader>

          <button onClick={() => navigate('/clients')}>Клиенты</button>
          <button onClick={() => navigate('/applications')}>Заявки на кредит</button>
          <button onClick={() => navigate('/contracts')}>Кредитные договоры</button>
      </SHeader>
  )
}