import React from 'react'
import { RuleModificationStepView, createInputFieldUpdater } from '../modification-common'

import InputField from '@/modules/shared/components/InputField'

const RuleModificationGeneral: RuleModificationStepView = (
  { inProgressRule, updateField }
) => (
    <div>
      <InputField
        label="cns.rule.field.name.label"
        value={inProgressRule.name || ""}
        onChange={createInputFieldUpdater(updateField)('name')} />

      <InputField
        label="cns.rule.field.description.label"
        multiline
        rows={3}
        value={inProgressRule.description || ""}
        onChange={createInputFieldUpdater(updateField)('description')} />
    </div>
  )

export default RuleModificationGeneral
