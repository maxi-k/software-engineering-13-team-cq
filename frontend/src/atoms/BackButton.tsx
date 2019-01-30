import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { BMWButton as Button } from '@fleetdata/shared/components/button'

interface DispatchAttributes {
  label?: string,
  onClick(event: React.SyntheticEvent<any, any>): void
}
type BackButtonProps = DispatchAttributes & React.HTMLAttributes<HTMLButtonElement>

const StyledBackButton = styled(Button)`
  width: min-content;
`
const BackButton: React.SFC<BackButtonProps> = (
  { label = "cns.navigation.back.label", ...props }
) => (
    <StyledBackButton
      {...props}
      primary="false" >
      <FormattedMessage id={label} />
    </StyledBackButton>
  )

export default BackButton
