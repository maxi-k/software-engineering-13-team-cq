import { rem } from 'polished';
import { fontL } from '@/fleetdata/shared/styles/variables';
import styled from 'styled-components';

export const StyledEmptyNote = styled.div`
  width: 100%;
  height: ${rem(400)};
  text-align: center;
  display: flex;
  justify-content: center;
  justify-items: center;
  align-items: center;
  align-content: center;
  font-size: ${fontL};
`;
