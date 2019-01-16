import React from 'react'
import { RuleCreationStepView, createInputFieldUpdater } from './common'

import InputField from '@/atoms/InputField'

const RuleCreationGeneral: RuleCreationStepView = (
  { inProgressRule, updateField }
) => (
    <div>
      <InputField
        label="cns.rule.field.name.label"
        value={inProgressRule.name || ""}
        onChange={createInputFieldUpdater(updateField, 'name')} />

      <InputField
        label="cns.rule.field.description.label"
        multiline
        rows={3}
        value={inProgressRule.description || ""}
        onChange={createInputFieldUpdater(updateField, 'description')} />
    </div>
  )

export default RuleCreationGeneral
