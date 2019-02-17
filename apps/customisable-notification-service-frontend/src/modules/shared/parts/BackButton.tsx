import React from 'react'
import { goBack } from 'connected-react-router'

import { StateMapper, DispatchMapper, connect } from '@/state/connector'
import StyledBackButton from '../components/BackButton'

interface DispatchAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}
type BackButtonProps = DispatchAttributes & React.HTMLAttributes<HTMLButtonElement>

const BackButton: React.SFC<BackButtonProps> = (props) => (
  <StyledBackButton {...props} />
)

const mapStateToProps: StateMapper<{}, {}> = (state, ownProps) => ({})
const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, ownProps) => ({
  onClick: () => dispatch(goBack())
})
export default connect(mapStateToProps, mapDispatchToProps)(BackButton)
