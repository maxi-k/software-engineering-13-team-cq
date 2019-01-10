import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import Typography from '@material-ui/core/Typography'

export interface FieldDescriptorAttributes {
  label: string,
  content: string | React.ReactNode
}
export type FieldDescriptorProps = FieldDescriptorAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledDescriptor = styled.div`

`

const StyledDescriptorField = styled.div`
    background-color: #EFEFEF;
    padding: 0.7rem;
`

const FieldDescriptor: React.SFC<FieldDescriptorProps> = ({ label, content, ...props }) => (
  <StyledDescriptor {...props}>
    <Typography variant="overline">
      <FormattedMessage id={label} />
    </Typography>
    <StyledDescriptorField>
      {typeof content === 'string'
        ? content
        : <Typography variant="body1">{content}</Typography>}
    </StyledDescriptorField>
  </StyledDescriptor>
)

export default FieldDescriptor
