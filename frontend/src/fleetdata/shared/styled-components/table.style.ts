import {
  Table,
  TableCell,
  TableHead,
  TablePagination,
  TableRow,
  TableSortLabel,
} from '@material-ui/core';
import { rem } from 'polished';
import styled from 'styled-components';

import { media } from '@/fleetdata/utils/media-query';
import {
  blue,
  darkgray,
  disabledgray,
  fontNormal,
  hoverlightgray,
  lightblue,
  lightgray,
} from '../styles/variables';

export const StyledTable = styled(Table)`
  font-family: 'Arial', 'sans-serif';
  border-color: ${lightgray};
  border-collapse: initial;
  table-layout: fixed;
`;

export const StyledTableHead = styled(TableHead)`
  &.sticky {
    z-index: 3;
    position: fixed;
    top: 96px;
    overflow-x: hidden;
  }
`;

export const StyledTableRow = styled(TableRow)`
  transition: background-color 0.1s ease-in-out;
  position: relative;

  &.clickable {
    cursor: pointer;
  }

  &:hover {
    background-color: ${hoverlightgray};
  }

  &:last-child {
    border-bottom: 6px solid ${lightgray};
  }
`;

export const StyledTableCell = styled(TableCell).attrs<{
  width?: number;
  maxwidth?: number;
  isicon?: boolean;
}>({
  width: (props: { width?: number }) => props.width && rem(props.width),
  maxwidth: (props: { maxwidth?: number }) =>
    props.maxwidth && rem(props.maxwidth),
})`
  border-bottom: 1px solid ${lightgray};
  padding: 0 ${rem(8)};
  font-family: 'BMWGroupCondensed', 'sans-serif';
  letter-spacing: 0.3px;
  color: ${darkgray};
  font-size: 14px;
  vertical-align: middle;
  max-width: ${props => props.maxwidth || props.width};
  width: ${props => props.width};
  box-sizing: border-box;
  height: 58px;
  & > span {
    font-family: 'BMWGroupCondensed', 'sans-serif';
  }

  & > svg {
    vertical-align: middle;
    padding-left: ${rem(8)};
  }

  &:last-child {
    padding-right: ${rem(8)};
  }
`;

export const StyledIconTableCell = styled(StyledTableCell)`
  padding: 0 !important;
`;

export const StyledCheckboxTableCell = StyledTableCell.extend`
  padding-left: ${rem(4)};
`;

export const StyledImageTableCell = StyledTableCell.extend`
  padding: 4px 0;
  width: 100%!importtant;
  justufy-items: flex-end;
`;

export const StyledInfoContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-content: center;
  padding-bottom: ${rem(8)};
  font-size: ${rem(13)};
  color: ${darkgray};
  min-height: ${rem(50)};
`;

export const NumVehiclesContainer = styled.div`
  display: flex;
  align-items: center;
`;

export const StyledTableSortLabel = styled(TableSortLabel)`
  color: ${lightblue};

  :hover,
  :focus {
    color: ${blue};
  }
`;

export const StyledTableContainer = styled.div`
  width: 100%;
  height: 100%;
  overflow-x: auto;
  position: relative;
  outline: none;

  ${media.md`padding-top:2rem`};
`;

export const StyledTablePagination = styled(TablePagination)`
  margin-bottom: ${rem(52)};

  &,
  & .caption,
  & .select,
  & .input {
    font-size: ${fontNormal};
    color: ${lightblue};
  }

  & .select {
    padding-right: 1.8rem;
  }

  & svg {
    fill: ${lightblue};
  }

  & button[disabled] svg {
    opacity: 0.3;
  }
`;

export const StyledHeaderTableRow = styled(TableRow)`
  th {
    position: flex;
    z-index: 3;
    background-color: ${lightgray};
  }
`;

export interface IStyledEditbaeTableCellContainerProps
  extends React.Props<HTMLDivElement>,
    React.HTMLAttributes<HTMLDivElement> {
  width?: number;
  minwidth?: number;
  maxwidth?: number;
}

export const StyledEditableTableCellContainer = styled.div`
  padding-right: ${rem(4)};
  padding-left: ${rem(4)};
  position: relative;
  width: ${(props: any) => (props.width && rem(props.width)) || 'auto'};
  min-width: ${(props: any) =>
    (props.minwidth && rem(props.minwidth)) || 'auto'};
  max-width: ${(props: any) =>
    (props.maxwidth && rem(props.maxwidth)) || 'auto'};
  min-height: 58px;
  display: flex;
  align-items: center;
  height: 58px;
  overflow: hidden;
  display: flex;
  justify-content: left;
  & > span {
    font-family: 'BMWGroupCondensed', 'sans-serif';
  }

  & > svg {
    vertical-align: middle;
    padding-left: ${rem(8)};
  }

  &.editMode {
    background-color: white;
    z-index: 9000;
  }
`;

export const StyledEditableTableCell = StyledTableCell.extend`
  padding: 0;

  &.disabled {
    color: ${disabledgray};
  }
`;

const StyledTableShadow = styled.div`
  width: 4.5rem;
  height: 100%;
  position: absolute;
  top: 0;
  background: rgb(255, 255, 255);
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 1) 0%,
    rgba(255, 255, 255, 0) 100%
  );
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.25s ease-in-out;

  &.is-visible {
    opacity: 1;
  }
`;

export const StyledTableShadowStart = StyledTableShadow.extend`
  left: 0;
`;

export const StyledTableShadowEnd = StyledTableShadow.extend`
  right: 0;
  transform: rotate(180deg);
`;
