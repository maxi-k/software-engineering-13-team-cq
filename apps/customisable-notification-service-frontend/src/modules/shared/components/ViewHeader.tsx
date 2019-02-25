import React from 'react'
import styled from 'styled-components'
import { FormattedMessage, MessageValue } from 'react-intl'
import Typography from '@material-ui/core/Typography'

export interface ViewHeaderAttributes {
  title: string | React.ReactNode,
  titlePrefix?: string | React.ReactNode,
  titleSuffix?: string | React.ReactNode,
  titleValues?: { [key: string]: MessageValue | JSX.Element }
}
export type ViewHeaderProps = ViewHeaderAttributes & React.HTMLAttributes<HTMLHeadingElement>

export const StyledViewHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
`

const ViewHeader: React.SFC<ViewHeaderProps> = (
  { title, titlePrefix = "", titleSuffix = "", titleValues = {}, children, ...props }
) => (
    <StyledViewHeader {...props}>
      <Typography variant="h5">
        {titlePrefix}
        {typeof title === 'string' ?
          <FormattedMessage id={title} values={titleValues} />
          : { title }}
        {titleSuffix}
      </Typography>
      {children}
    </StyledViewHeader>
  )

export default ViewHeader
