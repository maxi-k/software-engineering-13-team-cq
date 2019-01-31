import { DialogContentText, Paper, Popover, Popper } from '@material-ui/core';
import { rem } from 'polished';
import styled from 'styled-components';

import { darkgray, spaceXS, white } from '../../styles/variables';

export const StyledPopover = styled(Popover)`
  .paper {
    opacity: 0 !important;
  }
`;

export const StyledPopper = styled(Popper)`
  z-index: 9300;
  &[x-placement*='bottom'] .arrow {
    top: 0;
    left: 0;
    margin-top: -0.9em;
    width: 3em;
    height: 1em;
    &::before {
      border-width: 0 1em 1em 1em;
      border-color: transparent transparent ${white} transparent;
    }
  }
  &[x-placement*='top'] .arrow {
    bottom: 0;
    left: 0;
    margin-bottom: -0.9em;
    width: 3em;
    height: 1em;
    &::before {
      border-width: 1em 1em 0 1em;
      border-color: ${white} transparent transparent transparent;
    }
  }
  &[x-placement*='right'] .arrow {
    left: 0;
    margin-left: -0.9em;
    height: 3em;
    width: 1em;
    &::before {
      border-width: 1em 1em 1em 0;
      border-color: transparent ${white} transparent transparent;
    }
  }
  &[x-placement*='left'] .arrow {
    right: 0;
    margin-right: -0.9em;
    height: 3em;
    width: 1em;
    &::before {
      border-width: 1em 0 1em 1em;
      border-color: transparent transparent transparent ${white};
    }
  }

  & .move-down {
    transform: translateY(${rem(250)});
    position: relative;
  }
`;

export const Arrow = styled.span.attrs({ className: 'arrow' })`
  z-index: 1401;
  position: absolute;
  font-size: 7px;
  width: 3em;
  height: 3em;

  &::before {
    content: '';
    margin: auto;
    display: block;
    width: 0;
    height: 0;
    border-style: solid;
  }
`;

export const StyledPopperContainer = styled(Paper)`
  padding-bottom: ${spaceXS};
  max-width: ${rem(352)};
`;

export const StyledDialogContentText = styled(DialogContentText)`
  color: ${darkgray};
`;
