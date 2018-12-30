import { Grid } from '@material-ui/core';
import { rem } from 'polished';
import styled from 'styled-components';
import { sizes } from '../../utils/media-query';

export const PageContainer = styled.main`
  display: flex;
  flex-direction: row;
`;

export const StyledGrid = styled(Grid)`
  overflow-x: initial;
  overflow-y: visible;
  height: 100%;
`;

export const StyledNavGrid = styled(Grid)`
  margin-left: -8px;
`;

export const MainDiv = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
  min-height: calc(100vh - 166px);
  padding-bottom: ${rem(38)};
`;

export const ContentDiv = styled.div`
  max-width: ${sizes.xxl - 1}px;
  padding: 0 ${rem(40)} 0 ${rem(40)};
  box-sizing: border-box;
  margin-left: auto;
  margin-right: auto;
  margin-top: 8rem;
`;

export const StyledTopPaddingContainer = styled.div`
  display: flex;
  padding: ${rem(32)} 0 ${rem(4)} 0;
  width: 100%;
  flex-direction: column;
`;
