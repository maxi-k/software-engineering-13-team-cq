import React from 'react'
import { connect, StateMapper, DispatchMapper } from '@/state/connector'
import { viewRule } from '@/state/rule'
import { VehicleDataType } from '@/model'

import { RuleTileProps } from '@/atoms/RuleTile'
import RuleOverview, { RuleOverviewProps, SelectRuleType } from '@/molecules/RuleOverview'
import RuleOverviewHeader from '@/organisms/RuleOverviewHeader'

import styled from 'styled-components'

export interface RuleOverviewDispatchProps {
  selectRule: SelectRuleType
}

// export interface RuleOverviewPageAttributes {
// }
export type RuleOverviewPageProps = RuleOverviewProps // RuleOverviewPageAttributes &
  & React.HTMLAttributes<HTMLDivElement>

const StyledOverviewPage = styled.div`

`

const RuleOverviewPage: React.SFC<RuleOverviewPageProps> =
  ({ ...props }) => (
    <StyledOverviewPage>
      <RuleOverviewHeader />
      <RuleOverview {...props} />
    </StyledOverviewPage>
  )

// TODO: FIXME: Fix with actual props
const mockRuleTileProps: RuleTileProps = {
  rule: {
    id: 1,
    name: 'Rule Name',
    description: 'Rule Description for an examplary Rule',
    aggregatorDescription: 'Sent every Tuesday, 9:00 AM',
    dataSources: [
      VehicleDataType.Engine,
      VehicleDataType.Battery
    ]
  }
}

const mockRuleOverviewProps: RuleOverviewProps = {
  rules: [mockRuleTileProps.rule, { ...mockRuleTileProps.rule, id: 2, name: 'Rule Name 2' }],
  addRule: () => alert('add rule'),
  selectRule: (_, rule) => alert('select rule: ' + rule.id),
  isFetching: false,
  hasFetchError: false
}


const mapStateToProps: StateMapper<{}, {}> = () => ({
  ...mockRuleOverviewProps
})

const mapDispatchToProps: DispatchMapper<{}, RuleOverviewDispatchProps> = (dispatch, props) => ({
  selectRule: (event, rule) => dispatch(viewRule(rule.id))
})

// TODO: FIXME: Fetch on component mount
export default connect(mapStateToProps, mapDispatchToProps)(RuleOverviewPage)
