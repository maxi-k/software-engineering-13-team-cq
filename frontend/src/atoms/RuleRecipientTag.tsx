import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { NotificationRecipient } from '@/model'

export interface RuleRecipientTagAttributes {
  recipient: NotificationRecipient
}
type RuleRecipientTagProps = RuleRecipientTagAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledTypeTag = styled.span`
    display: inline-block;
    background-color: #CECECE;
    padding: 0.5rem;
`

const StyledValueTag = styled.div`
    display: inline-block;
    padding: 0.5em;
`

const StyledRuleRecipient = styled.div`
    display: inline-block;
    background-color: #DEDEDE;
    border-radius: 2px;
    margin: 0.5rem;
    padding: 0;
    overflow: hidden;
`

const RuleRecipientTag: React.SFC<RuleRecipientTagProps> = ({ recipient, ...props }) => (
  <StyledRuleRecipient {...props}>
    <StyledTypeTag>
      <strong>
        <FormattedMessage id={`cns.rule.recipient.${recipient.type.toLowerCase()}.label`}/>
      </strong>
    </StyledTypeTag>
    <StyledValueTag>
      {recipient.value}
    </StyledValueTag>
  </StyledRuleRecipient>
)

export default RuleRecipientTag
