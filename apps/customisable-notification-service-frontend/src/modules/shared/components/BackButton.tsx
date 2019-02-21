import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { BMWButton as Button } from '@fleetdata/shared/components/button'

interface BackButtonAttributes {
  label?: string
  fullWidth?: boolean
  onClick(event: React.SyntheticEvent<any, any>): void
}

export type BackButtonProps = BackButtonAttributes
                            & React.HTMLAttributes<HTMLButtonElement>

const StyledBackButton = styled<BackButtonProps & { primary: string }>(
  ({ fullWidth, ...buttonProps }) => <Button {...buttonProps} />
)`
  ${props => props.fullWidth ? '' : 'width: min-content;'}
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
