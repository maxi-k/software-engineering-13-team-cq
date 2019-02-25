import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { messageFromError } from '@/services/response-service'
import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import { BMWButton } from '@fleetdata/shared/components/button';
import EditIcon from '@fleetdata/shared/components/icons/edit.icon'
import DeleteIcon from '@fleetdata/shared/components/icons/delete.icon'

import EmbeddedIcon from '@/modules/shared/components/EmbeddedIcon'
import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import ViewHeader from '@/modules/shared/components/ViewHeader'
import BackButton from '@/modules/shared/parts/BackButton'
import FlexBar from '@/modules/shared/components/FlexBar'

import RuleDetails, { StyledFieldSeparator } from '../parts/RuleDetails'

interface RuleDetailAttributes {
  rule: NotificationRuleDetail,
  toggleEditRule(rule: NotificationRuleDetail): void,
  toggleDeleteRule(rule: NotificationRuleDetail): void
}

type RuleDetailProps = FetchingAttributes
  & RuleDetailAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetail = styled.div`
`

const FlexButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;

  ${BMWButton} {
    margin-right: 1rem;
  }
`

const RuleDetail: React.SFC<RuleDetailProps> = ({
  isFetching, hasFetchError, rule,
  toggleEditRule, toggleDeleteRule,
  ...props
}) => {

  if (hasFetchError) {
    return (
      <ErrorMessage message={
        <FormattedMessage id={messageFromError(hasFetchError)} />
      } />
    )
  }
  if (isFetching || typeof rule === 'undefined' || rule === null) {
    return <LoadingIndicator isCentral={true} />
  }

  const onClickDeleteRule = () => toggleDeleteRule(rule)
  const onClickEditRule = () => toggleEditRule(rule)

  return (
    <StyledRuleDetail {...props}>
      <ViewHeader
        style={{ padding: '1rem' }}
        title="cns.page.ruleDetail.title"
        titleValues={{ ruleName: rule.name }}
      />
      <FlexBar padded>
        <FlexButtonWrapper>
          <BMWButton
            icon={<EmbeddedIcon component={EditIcon} />}
            onClick={onClickEditRule} >
            <FormattedMessage id="cns.rule.action.edit.label" />
          </BMWButton>
          <BMWButton
            icon={<EmbeddedIcon component={DeleteIcon} />}
            onClick={onClickDeleteRule} >
            <FormattedMessage id="cns.rule.action.delete.label" />
          </BMWButton>
        </FlexButtonWrapper>
        <BackButton goToHome />
      </FlexBar>
      <StyledFieldSeparator />
      <RuleDetails rule={rule} />
    </StyledRuleDetail >
  )
}

export default RuleDetail
