import { rem, transparentize } from 'polished';
import * as React from 'react';
import styled from 'styled-components';

import {
  black,
  blue,
  buttonicon,
  darkgray,
  hovermediumgray,
  lightblue,
  mediumgray,
  white,
} from '../styles/variables';

interface IBMWButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  small?: string;
  width?: 'auto' | '100%' | number;
  icon?: JSX.Element | React.ReactNode;
  iconleft?: JSX.Element | React.ReactNode;
  variant?: 'text' | 'flat' | undefined | string;
  label?: string;
  primary?: string;
}

const getVariantValue = (
  variant: 'text' | 'flat' | undefined | string,
  values: {
    text: string | number;
    flat: string | number;
    default: string | number;
  },
): string | number => {
  switch (variant) {
    case 'text':
      return values.text;
    case 'flat':
      return values.flat;
    default:
      return values.default;
  }
};

const getVariant = (values: {
  text: string | number;
  flat: string | number;
  default: string | number;
}) => (props: IBMWButtonProps) => getVariantValue(props.variant, values);

class UnstyledBMWButton extends React.Component<IBMWButtonProps> {
  public render() {
    return (
      <button
        {...this.props}
        className={
          this.props.primary === 'true'
            ? 'primary' + ` ${this.props.className}`
            : this.props.className
        }
      >
        {this.props.iconleft}
        {(this.props.label || this.props.children) && (
          <span>
            {this.props.label}
            {this.props.children}
          </span>
        )}
        {this.props.icon}
      </button>
    );
  }
}

export const BMWButton = styled((props: IBMWButtonProps) => (
  <UnstyledBMWButton {...props} />
))`
  background-color: ${getVariant({
  text: 'transparent',
  flat: transparentize(0.35, lightblue),
  default: hovermediumgray,
})};
  font-weight: ${getVariant({
  text: 'bold',
  flat: 'normal',
  default: 'bold',
})};
  font-size: 18px;
  color: ${getVariant({
  text: mediumgray,
  flat: darkgray,
  default: white,
})};
  font-family: 'BMWGroupCondensed', 'Arial', 'sans-serif';
  border-radius: ${getVariant({ text: 0, flat: rem(16), default: 0 })};
  padding-right: ${getVariant({
  text: rem(16),
  flat: rem(8),
  default: '1.813rem',
})};
  padding-left: ${getVariant({
  text: rem(16),
  flat: rem(8),
  default: '1.813rem',
})};
  padding-bottom: 0;
  padding-top: 2px;

  text-transform: ${getVariant({
  text: 'uppercase',
  flat: 'normal',
  default: 'uppercase',
})};
  text-decoration: none;
  text-align: center;
  border: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  align-content: center;
  position: relative;
  height: ${(props: IBMWButtonProps) =>
    props.small === 'true' ? rem(45) : rem(50)};
  max-width: 22.5rem;
  overflow: hidden;
  min-height: 2.4375rem;
  transition: 0.2s background-color, color;

  cursor: pointer;
  margin: 0;
  width: ${(props: IBMWButtonProps) => {
    if (typeof props.width === 'number') {
      return rem(props.width);
    } else {
      return props.width === 'auto' ? 'auto' : '100%';
    }
  }}

  &:hover,
  &:focus {
    background-color: ${getVariant({
    text: 'transparent',
    flat: transparentize(0.35, blue),
    default: black,
  })};
    color: ${getVariant({
    text: hovermediumgray,
    flat: darkgray,
    default: white,
  })};
    outline: none;
  }

  & > img {
    margin-top: -2px;
  }

  & > svg {
    height: 75% !important;
    width: 2rem !important;
    fill: ${buttonicon};
  }

  & > span {
    margin: 0 ${getVariant({
    text: 0,
    flat: '24px',
    default: 0,
  })} 0 0;
  }

  &.primary {
    color: ${getVariant({
    text: lightblue,
    flat: mediumgray,
    default: white,
  })};
    background-color: ${getVariant({
    text: 'transparent',
    flat: transparentize(0.35, lightblue),
    default: lightblue,
  })};

    &:hover,
    &:focus {
      background-color: ${getVariant({
    text: 'transparent',
    flat: blue,
    default: blue,
  })};
      color: ${getVariant({
    text: blue,
    flat: mediumgray,
    default: white,
  })};
      outline: none;
    }
  }
`;
