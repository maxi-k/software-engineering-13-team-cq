import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  FormControlLabel,
  Input,
  InputLabel,
  MenuItem,
} from '@material-ui/core';
import Select, { SelectProps } from '@material-ui/core/Select';
import { ellipsis, rem } from 'polished';
import * as React from 'react';
import styled from 'styled-components';

import {
  arialfont,
  BMWfont,
  bmwGreen,
  darkgray,
  fontS,
  fontXL,
  formgray,
  lightblue,
  lightgray,
  red,
  white,
} from '../../styles/variables';

const formBase = `font-family: 'Arial', sans-serif;
display: flex;
border: 1px solid #999;
border-radius: 0;
background: 0;
box-shadow: none;
font-size: 1rem;
color: #444;

&.has-error {
  border-bottom-color: ${red};
  border-bottom-width: 2px;
}

&.disabled {
  border-color: ${lightgray};
}
`;

interface IStyledDialogProps {
  minwidth?: number | string;
  maxwidth?: number | string;
}

export const StyledDialog = styled(Dialog)`
  & .paper {
    padding: ${rem(40)};
    min-width: ${(props: IStyledDialogProps) =>
      (props.minwidth && props.minwidth) || rem(548)};
    max-width: ${(props: IStyledDialogProps) =>
      (props.maxwidth && props.maxwidth) || rem(548)};
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    z-index: 1400;
    max-height: calc(100vh - ${rem(180)});
  }

  &.full-height > .paper {
    min-height: calc(100vh - ${rem(180)});
  }
`;

export const StyledDialogTitle = styled(DialogTitle)`
  padding: 0;
  margin-bottom: ${rem(24)};

  & h6 {
    font-family: ${BMWfont};
    font-size: ${fontXL};
    color: ${lightblue};
    font-weight: bold;
    display: flex;
    flex-direction: row;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-content: space-between;
  }
`;

export const StyledDialogContent = styled(DialogContent)`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

export const StyledFormControl = styled(FormControl)`
  padding-top: ${rem(8)};
  display: flex;
  flex-direction: column;
  width: 100%;
`;

export const StyledFormLabel = styled.span`
  padding-bottom: ${rem(32)};
  display: block;
`;

export const StyledDialogActions = styled(DialogActions)`
  display: flex;
  justify-content: flex-end;
  margin: 0 -4px;
  padding-top: ${rem(40)};

  & button:not(:last-child) {
    margin-right: ${rem(10)};
  }
`;

export const StyledMandatoryNote = styled.span`
  font-family: ${arialfont};
  color: ${formgray};
  display: block;
  text-align: right;
  padding-top: ${rem(7)};
`;

export const StyledFormikForm = styled.form`
  margin-top: ${rem(35)};
`;

export const StyledInput = styled(Input)`
  ${formBase};
  height: 53px;
  padding: 0 0 0 1rem;
  width: 100%;
  box-sizing: border-box;
  padding-right: 2rem;
  background-color: ${white};

  & .input {
    height: initial;
  }
`;

export const StyledTextField = styled.div`
  & .root {
    display: flex;
  }

  & input,
  & textarea {
    font-size: ${fontS};
  }
  border-bottom: 2px solid ${bmwGreen};
  width: 100%;

  &.has-error {
    border-bottom-color: ${red};
  }
`;

export const StyledFilterTextField = StyledTextField.extend`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  & .root {
    width: 100%;
    display: inline-flex;
    border-bottom: 2px solid ${bmwGreen};
    margin: 0 ${rem(8)};
  }
  & input,
  & textarea {
    font-size: ${fontS};
  }
  border-bottom: none;
  width: 100%;
`;

export const StyledInputLabel = styled.label`
  text-transform: uppercase;
  font-family: ${BMWfont};
  font-weight: bold;
  color: ${darkgray};
  padding: 1em 0;
  display: block;
  font-size: 16px;
`;

export const StyledSelect = styled(
  ({ isNavigation, ...props }: SelectProps & { isNavigation?: boolean }) => (
    <Select classes={{ select: 'select', icon: 'icon' }} {...props} />
  ),
)`
  margin-top: 0 !important;
  & .select {
    max-width: 100%;
    ${formBase};
    align-items: center;
    width: 100%;
    ${(ellipsis('100%') as any) as string};
    padding: 15px 1rem;
    box-sizing: border-box;
    height: ${rem(53)};
  }

  & .icon {
    padding-right: ${rem(8)};
  }
`;

export const StyledFormError = styled.div`
  color: ${red};
  font-size: ${fontS};
  min-height: ${rem(16)};
  padding-top: ${rem(4)};
  display: block;
`;

export const StyledSelectLabel = styled(InputLabel)`
  font-family: ${arialfont};
  font-size: 1rem;
  color: #555 !important;
  opacity: 0.42;
  border: 0;
  margin: 0;
  padding: 3px 0 0 1rem;
  display: block;
  min-width: 0;
  flex-grow: 1;
  box-sizing: content-box;
  background: none;
  vertical-align: middle;
  -webkit-tap-highlight-color: transparent;
`;

export const StyledMenuItem = styled(MenuItem)`
  &.selected {
    background-color: ${white};
  }
`;

export const MenuProps = {
  getContentAnchorEl: undefined,
  anchorOrigin: {
    vertical: 'bottom' as 'bottom',
    horizontal: 'left' as 'left' | 'right',
  },
  PaperProps: {
    style: {
      maxHeight: 250,
      width: 250,
    },
  },
};

export const StyledCheckboxLabel = styled(FormControlLabel)`
  & > span {
    font-weight: bold;
    text-transform: uppercase;
    height: auto;
  }
`;

export const StyledCheckboxError = styled.span`
  color: red;
  font-size: ${rem(12)};
  font-weight: normal;
`;

export const StyledFleetSelect = styled<any>(StyledSelect)`
  margin-bottom: ${(props: any) => {
    return props.isNavigation ? '1rem' : '0';
  }};
  background-color: ${(props: any) =>
    props.isNavigation ? lightblue : 'none'};
  & .select {
    color: ${(props: any) => (props.isNavigation ? white : darkgray)};
    font-size: ${(props: any) => (props.isNavigation ? rem(20) : '0.875rem')};
    padding: ${(props: any) =>
      props.isNavigation ? '15px 1.5rem 15px 1rem' : '6px 0 7px'};
    border: none;
    height: ${(props: any) => (props.isNavigation ? 'inherit' : '2rem')};
  }

  & .icon {
    fill: ${white};
  }
`;
