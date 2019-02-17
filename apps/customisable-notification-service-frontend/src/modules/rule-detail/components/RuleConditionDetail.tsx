import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { mapObjectToArray } from '@/utilities/collection-util'
import { RuleCondition, RuleConditionPredicate, VehicleDataField } from '@/model'

interface RuleConditionDetailAttributes {
  condition: RuleCondition
}

type RuleConditionDetailProps = RuleConditionDetailAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledRuleConditionDetail = styled.div`

`

const InlineVariable = styled.span`
  font-weight: bold;
  padding: 0 0.35em;
`

const PredicateWrapper = styled.div`
  padding-left: 1rem;
`

const StyledPredicate = styled.div`
  padding-top: 0.5rem;
`

const formatPredicateField = (dataField: VehicleDataField<any>): string => (
  `cns.predicate.field.${dataField.vehicleDataType.toLowerCase()}.${dataField.predicateField.fieldName}.label`
)

const formatComparisonType = (comparisonType: string): string => (
  `cns.predicate.comparison.${comparisonType}.label`
)

const RuleConditionDetail: React.SFC<RuleConditionDetailProps> = ({ condition, ...otherProps }) => (
  <StyledRuleConditionDetail {...otherProps}>
    <FormattedMessage id="cns.predicate.counter.beforetext" />
    <InlineVariable>
      <FormattedMessage id={'cns.predicate.counter.' + condition.logicalConnective.toLowerCase()} />
    </InlineVariable>
    <FormattedMessage id="cns.predicate.counter.aftertext" />
    <PredicateWrapper>
      {
        mapObjectToArray(condition.predicates, (key: string | number, predicate: RuleConditionPredicate<any>) => (
          <StyledPredicate key={key}>
            <FormattedMessage id="cns.condition.selector.beforetext" />
            <InlineVariable>
              <FormattedMessage id={formatPredicateField(predicate.appliedField)} />
            </InlineVariable>
            <FormattedMessage id="cns.condition.selector.aftertext" />
            <InlineVariable>
              <FormattedMessage id={formatComparisonType(predicate.comparisonType)} />
            </InlineVariable>
            <InlineVariable>
              {predicate.comparisonConstant}
            </InlineVariable>
          </StyledPredicate>
        ))
      }
    </PredicateWrapper>
  </StyledRuleConditionDetail>
)

export default RuleConditionDetail
