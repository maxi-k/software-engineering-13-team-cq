import { Paper } from '@material-ui/core';
import { ellipsis, rem } from 'polished';
import styled from 'styled-components';

import { media } from '../../utils/media-query';
import { lightblue, lightgray, white } from '../styles/variables';

export const StyledSelection = styled.div`
  width: 100%;
  cursor: pointer;
  margin-bottom: 5rem;
  ${media.md`
  margin-bottom: 0;
    max-width: 100%;
  `};
`;

export const Container = styled(Paper)`
  border-radius: 0;
  margin-bottom: ${rem(16)};
  color: ${lightblue};
  &.active > div {
    background-color: ${lightblue} !important;
    color: ${white};
    & svg {
      fill: ${white} !important;
    }
  }

  ${media.md`
  min-width: ${rem(240)};
  margin-right: ${rem(8)}
`};
`;

export const InnerContainer = styled.div`
  position: relative;
  height: ${rem(120)};
  background-color: ${lightgray};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: background-color 0.2s ease;
  padding: ${rem(8)};

  & .nav-label {
    font-size: ${rem(24)};
    ${ellipsis('100%') as string};
  }

  &:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }
`;

export const StyledSelectorContainer = styled.div`
  display: flex;
  flex-direction: column;
  ${media.md`
    flex-direction: row;
    overflow: auto;
    width: 100%;
  `};
`;
