import { Card, CardContent, Grid } from '@material-ui/core';
import { rem, transparentize } from 'polished';
import { BMWButton } from '@/fleetdata/shared/components/button';
import AvatarIcon from '@/fleetdata/shared/components/icons/avatar.icon';
import {
  darkgray,
  fontL,
  lightgray,
  mediumgray,
} from '@/fleetdata/shared/styles/variables';
import styled from 'styled-components';

export const StyledNoUsersMessage = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  min-height: 50vh;
  font-size: ${fontL};
`;

export const StyledUserCardWrapper = styled(Grid).attrs({
  container: true,
  spacing: 32,
})`
  display: flex;
  flex-wrap: wrap;
`;

export const StyledCard = styled(Card)`
  width: 100%;
  min-height: ${rem(288)};
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: ${lightgray};
  position: relative;
`;

export const StyledCardName = styled.div`
  font-size: ${fontL};
  color: ${darkgray};
`;

export const StyledCardEmail = styled.div`
  color: ${mediumgray};
  padding-top: 0.5rem;
`;

export const StyledCardHeader = styled.div`
  display: flex;
  flex-direction: column;
  padding: ${rem(18)} 0;
`;

export const StyledCardContent = styled(CardContent)`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: wrap;
`;

export const StyledCardIcon = styled(AvatarIcon)`
  padding-top: 4px;
`;

export const StyledCustomerHeader = styled.h1`
  min-width: 33%;
`;

export const StyledHeaderWrapper = styled.div`
  display: flex;
  width: 100%;
  flex-direction: row;
  justify-content: space-between;
  height: ${rem(152)};
  flex-wrap: wrap;
`;

export const StyledDeactivatedWrapper = styled.div`
  position: absolute;
  display: flex;
  height: 100%;
  width: 100%;
  background-color: ${transparentize(0.2, lightgray)};
  align-items: center;
  justify-content: center;
  z-index: 900;
  cursor: unset;
`;

export const StyledLockIconWrapper = styled.div`
  z-index: 901;
`;

export const ReactivateButton = styled(BMWButton)`
  z-index: 902;
  pointer-events: auto;
`;
