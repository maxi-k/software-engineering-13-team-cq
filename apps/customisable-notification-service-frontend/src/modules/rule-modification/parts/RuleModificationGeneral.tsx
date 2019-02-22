import React from 'react'
import {
  RuleModificationStepView,
  createInputFieldUpdater,
  createCheckboxUpdater,
} from '../modification-common'
import { FormattedMessage } from 'react-intl'

import Switch from '@material-ui/core/Switch'
import FormControlLabel from '@material-ui/core/FormControlLabel'
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
      <FormControlLabel
        control={
          <Switch
            checked={inProgressRule.ownerAsAdditionalRecipient}
            onChange={createCheckboxUpdater(updateField)('ownerAsAdditionalRecipient')}
            value="ownerAsAdditionalRecipient" />
        }
        label={<FormattedMessage id="cns.rule.field.ownerAsAdditionalRecipient.label" />} />
    </div>
  )

export default RuleModificationGeneral
