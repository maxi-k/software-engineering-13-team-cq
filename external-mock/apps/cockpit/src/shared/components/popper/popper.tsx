import { DialogActions, DialogContent, DialogTitle } from '@material-ui/core';
import { PopperPlacementType, PopperProps } from '@material-ui/core/Popper';
import update from 'immutability-helper';
import * as React from 'react';

import { PopoverProps } from '@material-ui/core/Popover';
import { BMWButton } from '../button';
import {
  Arrow,
  StyledDialogContentText,
  StyledPopover,
  StyledPopper,
  StyledPopperContainer,
} from './popper.style';

interface IProps {
  refEl: HTMLElement | null;
  title?: string;
  open: boolean;
  cancelLabel?: string;
  handleCancel?: () => void;
  OKLabel?: string;
  handleOK?: (e: React.MouseEvent) => void;
  placement?: PopperPlacementType;
}

interface IState {
  arrowRef: HTMLElement | null;
}

export class BMWPopper extends React.Component<
  IProps & PopoverProps & PopperProps,
  IState
> {
  public state: IState = {
    arrowRef: null,
  };

  public handleArrowRef = (node: any) => {
    this.setState((prevState: IState) =>
      update(prevState, { $set: { arrowRef: node } }),
    );
  };

  public render() {
    return (
      <StyledPopover
        open={this.props.open}
        onClose={this.props.handleCancel}
        classes={{ paper: 'paper' }}
      >
        <StyledPopper
          open={this.props.open}
          anchorEl={this.props.refEl}
          placement={this.props.placement || 'top'}
          modifiers={{
            arrow: { enabled: true, element: this.state.arrowRef },
            flip: {
              enabled: true,
            },
            preventOverflow: {
              enabled: true,
              boundariesElement: 'scrollParent',
            },
          }}
        >
          {() => (
            <div className={this.props.className}>
              <Arrow innerRef={this.handleArrowRef} />
              <StyledPopperContainer elevation={8}>
                {this.props.title !== undefined && (
                  <DialogTitle>{this.props.title}</DialogTitle>
                )}
                {this.props.children !== undefined && (
                  <DialogContent>
                    <StyledDialogContentText>
                      {this.props.children}
                    </StyledDialogContentText>
                  </DialogContent>
                )}
                <DialogActions>
                  {this.props.handleCancel !== undefined && (
                    <BMWButton
                      variant="text"
                      onClick={this.props.handleCancel}
                      width="auto"
                      type="reset"
                    >
                      {this.props.cancelLabel}
                    </BMWButton>
                  )}
                  {this.props.handleOK !== undefined && (
                    <BMWButton
                      variant="text"
                      onClick={this.props.handleOK}
                      className="primary"
                      width="auto"
                      type="submit"
                    >
                      {this.props.OKLabel}
                    </BMWButton>
                  )}
                </DialogActions>
              </StyledPopperContainer>
            </div>
          )}
        </StyledPopper>
      </StyledPopover>
    );
  }
}
