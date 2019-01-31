import { Checkbox } from '@material-ui/core';
import { CheckboxProps } from '@material-ui/core/Checkbox';
import * as React from 'react';
import styled from 'styled-components';

interface ICheckboxProps extends CheckboxProps {
  checkedcolor?: string;
}

export const StyledCheckbox = styled((props: CheckboxProps) => (
  <Checkbox {...props} classes={{ checked: 'checked' }} />
))`
  color: ${(props: ICheckboxProps) => props.checkedcolor};

  &.checked {
    color: ${(props: ICheckboxProps) => props.checkedcolor};
  }
`;
