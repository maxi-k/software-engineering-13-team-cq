import React from 'react'
import styled from 'styled-components'

import { goBack } from 'connected-react-router'
import { FormattedMessage } from 'react-intl'

import { StateMapper, DispatchMapper, connect } from '@/state/connector'
import { BMWButton as Button } from '@fleetdata/shared/components/button'
import BackIcon from '@fleetdata/shared/components/icons/chevron-left.icon'


interface DispatchAttributes {
  goBack(event: React.SyntheticEvent<any, any>): void
}
type BackButtonProps = DispatchAttributes & React.HTMLAttributes<HTMLButtonElement>

const StyledBackButton = styled(Button)`
  width: min-content;
`

const StyledBackIcon = styled(BackIcon)`
  padding-right: 1rem;
`

const BackButton: React.SFC<BackButtonProps> = ({ goBack, ...props }) => (
  <StyledBackButton
    {...props}
    onClick={goBack}
    primary="false"
    iconleft={<StyledBackIcon fill={"#fff"} />}>
    <FormattedMessage id="cns.navigation.back.label" />
  </StyledBackButton>
)

const mapStateToProps: StateMapper<{}, {}> = (state, ownProps) => ({})
const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, ownProps) => ({
  goBack: () => dispatch(goBack())
})


export default connect(mapStateToProps, mapDispatchToProps)(BackButton)
