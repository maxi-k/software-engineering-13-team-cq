import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'
import Typography from '@material-ui/core/Typography'

export interface ViewHeaderAttributes {
  title: string | React.ReactNode,
  titlePrefix?: string | React.ReactNode,
  titleSuffix?: string | React.ReactNode
}
export type ViewHeaderProps = ViewHeaderAttributes & React.HTMLAttributes<HTMLHeadingElement>

export const StyledViewHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
`

const ViewHeader: React.SFC<ViewHeaderProps> = (
  { title, titlePrefix = "", titleSuffix = "", children, ...props }
) => (
    <StyledViewHeader {...props}>
      <Typography variant="h5">
        {titlePrefix}
        {typeof title === 'string' ?
          <FormattedMessage id={title} />
          : { title }}
        {titleSuffix}
      </Typography>
      {children}
    </StyledViewHeader>
  )

export default ViewHeader
