import { Button, Popover } from '@material-ui/core';
import { darken, rem } from 'polished';
import * as React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import { ButtonProps } from '@material-ui/core/Button';
import { media } from '../../../utils/media-query';
import { BMWfont, lightblue, lightgray } from '../../styles/variables';

export const BaseContainer = styled.div`
  height: ${rem(50)};
  width: 100%;
  display: flex;
  justify-content: space-between;
  color: ${lightblue};
  font-weight: bold;
`;

export const AddButton = styled<ButtonProps & { backgroundcolor?: string }>(
  ({ backgroundcolor, ...props }) => <Button {...props} />,
)`
  height: ${rem(50)};
  width: ${(props: { width?: number }) =>
    (props.width && rem(props.width)) || rem(260)};
  min-width: ${(props: { width?: number }) =>
    (props.width && rem(props.width)) || rem(260)};
  display: flex;
  font-size: ${rem(24)};
  font-weight: bold;
  border: none;
  border-radius: 0;
  color: ${lightblue};
  background-color: ${(props: { backgroundcolor?: string }) =>
    props.backgroundcolor || lightgray};
  text-transform: none;
  white-space: nowrap;
  padding: 0;

  &:hover {
    background-color: ${(props: { backgroundcolor?: string }) =>
      props.backgroundcolor
        ? darken(0.05, props.backgroundcolor)
        : darken(0.05, lightgray)} !important;
  }

  &:not(:last-child) {
    margin-right: ${rem(16)};
  }

  & .label {
    padding: 0 ${rem(16)};
    display: flex;
    width: 100%;
    justify-content: left;

    & > span {
      padding-left ${rem(20)};

      ${media.lg`padding-left: 4px`}
    }
`;
export const AddContainer = BaseContainer.extend`
  width: ${rem(48)};
  background-color: ${lightgray};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
`;

export const FormContainer = BaseContainer.extend`
  background-color: ${lightgray};
  display: flex;
  box-sizing: border-box;
`;

export const UploadForm = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding-left: ${rem(16)};
  align-content: center;
  align-items: center;
`;

export const UploadInputContainer = styled.div`
  position: relative;
  overflow: hidden;
  width: 37.5%;
  border-bottom: 1px solid ${lightblue};
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
`;

export const FileNameFakeInput = styled.input`
  background-color: transparent;
  border: none;
  height: 1.5rem;
  font-size: 1rem;
  color: ${lightblue};
  font-family: BMWGroupCondensed, Arial, sans-serif;
  width: 100%;
  cursor: pointer;
  padding: 0;
  background-image: url(assets/svg/next_24_navigation_negativ_default.svg);
  background-repeat: no-repeat;
  background-position: right 1.313rem center;

  &::placeholder {
    color: ${lightblue};
  }
`;

export const SubmitContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ErrorContainer = styled.div`
  color: black;
  font-size: medium;
  height: ${rem(25)};
`;

export const StyledFormControl = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-direction: column;
`;

export const StyledDowloadTemplateContainer = styled.div`
  display: flex;
  align-items: center;
  cursor: pointer;

  & > a {
    color: ${lightblue};
    font-weight: normal;
  }
`;

export const StyledActionWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  min-height: 90px;
  flex-wrap: wrap;
`;

export const StyledButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
`;

export const StyledPopover = styled(Popover)`
  & > .paper {
    width: ${rem(812)};
  }
`;

export const StyledLink = styled(Link)`
  background-color: ${lightgray};
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  font-family: ${BMWfont};
  font-weight: bold;
  font-size: ${rem(24)};
  color: ${lightblue};
  width: 100%;
  height: ${rem(216)};
  text-decoration: none;
  transition: background-color 0.2s ease-in-out;

  &:hover {
    background-color: ${darken(0.05, lightgray)};
  }
`;
