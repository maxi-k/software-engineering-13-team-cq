import { IconButton } from '@material-ui/core';
import * as React from 'react';
import styled from 'styled-components';

interface IProps {
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
  children: any;
  height?: number;
  width?: number;
  iconcolor?: string;
  hidehover?: 'true';
}

export const StyledIconButton = styled((props: IProps) => (
  <IconButton {...props} children={props.children} />
))`
  ${props => props.height && `height:${props.height - 2}px`};
  ${props => props.width && `width:${props.width - 2}px`};
  align-self: center;
  align-items: center;
  justify-content: center;
  box-sizing: content-box;

  &:hover {
    background-color: ${props =>
      props.hidehover ? 'transparent' : 'rgba(0, 0, 0, 0.08)'};
  }
`;
