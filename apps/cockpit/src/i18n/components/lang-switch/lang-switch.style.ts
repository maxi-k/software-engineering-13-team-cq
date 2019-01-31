import styled from 'styled-components';
import { white } from '../../../shared/styles/variables';

export const StyledSwitchBtn = styled.button`
  font-family: BMWGroupCondensed, helvetica, sans-serif;
  font-size: 0.844rem;
  font-weight: bold;
  line-height: 18px;
  color: #a3a3a3;
  padding-bottom: 7px;
  padding-right: 6px;
  padding-left: 6px;
  vertical-align: top;
  border: none;
  margin-right: 13px;
  margin-top: 5px;
  cursor: pointer;
  background-color: ${white};
  &.active {
    color: #444;
    border-bottom: 2px solid #444;
  }
  &:focus {
    outline: none;
  }
`;
