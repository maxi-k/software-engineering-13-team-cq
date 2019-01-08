import React from 'react'
// import { connect, StateType, StateMapper, DispatchMapper } from '@/state/connector'

import styled from 'styled-components'
import { rem, stripUnit } from 'polished'

import Typography from '@material-ui/core/Typography'
import { FormattedMessage } from 'react-intl'
import { defaultTilePadding } from '@/molecules/RuleOverview'

interface Attributes {

}
type Props = Attributes & React.HTMLAttributes<HTMLDivElement>

const StyledHeader = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 0.5rem ${1 + (stripUnit(rem(defaultTilePadding)) as number)}rem;
`

const RuleOverviewHeader: React.SFC<Props> = (props) => (
  <StyledHeader {...props}>
    <Typography variant="h5">
      <FormattedMessage id="cns.page.ruleOverview.title" />
    </Typography>
  </StyledHeader>
)


export default RuleOverviewHeader
