import React from 'react'
import styled from 'styled-components'

import { goBack } from 'connected-react-router'
import { FormattedMessage } from 'react-intl'

import { StateMapper, DispatchMapper, connect } from '@/state/connector'
import { BMWButton as Button } from '@fleetdata/shared/components/button'

interface DispatchAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}
type BackButtonProps = DispatchAttributes & React.HTMLAttributes<HTMLButtonElement>

const StyledBackButton = styled(Button)`
  width: min-content;
`
const BackButton: React.SFC<BackButtonProps> = (props) => (
  <StyledBackButton
    {...props}
    primary="false" >
    <FormattedMessage id="cns.navigation.back.label" />
  </StyledBackButton>
)

const mapStateToProps: StateMapper<{}, {}> = (state, ownProps) => ({})
const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, ownProps) => ({
  onClick: () => dispatch(goBack())
})

export default connect(mapStateToProps, mapDispatchToProps)(BackButton)
