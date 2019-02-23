import React from 'react'
import { RuleModificationStepView } from '../modification-common'
import RuleDetails from '@/modules/rule-detail/parts/RuleDetails'

const RuleModificationSummary: RuleModificationStepView = (
  { inProgressRule, updateField }
) => (
    <RuleDetails rule={inProgressRule} />
  )

export default RuleModificationSummary
