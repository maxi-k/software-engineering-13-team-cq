import React from 'react'
import { goBack, push } from 'connected-react-router'

import { StateMapper, DispatchMapper, connect } from '@/state/connector'
import StyledBackButton, { BackButtonProps as StyledBackButtonProps } from '../components/BackButton'

export interface BackButtonAttributes {
  goToHome?: boolean
}

interface DispatchAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}
type BackButtonProps = DispatchAttributes
  & StyledBackButtonProps
  & BackButtonAttributes
  & React.HTMLAttributes<HTMLButtonElement>

const BackButton: React.SFC<BackButtonProps> = ({ goToHome, ...props }) => (
  <StyledBackButton {...props} />
)

const mapStateToProps: StateMapper<{}, {}> = (state, ownProps) => ({})
const mapDispatchToProps: DispatchMapper<BackButtonAttributes, DispatchAttributes> = (dispatch, ownProps) => ({
  onClick: () => dispatch(ownProps.goToHome ? push('/') : goBack())
})
export default connect(mapStateToProps, mapDispatchToProps)(BackButton)
