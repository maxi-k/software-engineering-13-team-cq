import React from 'react'
import {
  RuleModificationStepView,
  createInputFieldUpdater,
  createCheckboxUpdater,
  FieldUpdateCreator
} from '../modification-common'
import { FormattedMessage } from 'react-intl'

import Switch from '@material-ui/core/Switch'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import InputField from '@/modules/shared/components/InputField'

import RecipientSelector from '../components/RecipientSelector'
import { NotificationRecipient, NotificationRecipientType } from '@/model';

const convertRecipientsToOptions = (recipients: NotificationRecipient[]) => (
  recipients.map((recipient) => ({ label: recipient.value, value: recipient.value, key: recipient.value }))
)

const createRecipientFieldUpdater: FieldUpdateCreator = updater => name => (
  updater(name, <T extends { value: any }>(selected: T[], newlySelected: T) => ({
    $set: selected.map((entry) => (
      entry.value === 'undefined'
        ? ''
        : (
          entry.value.includes('@')
            ? { type: NotificationRecipientType.Email, value: entry.value }
            : { type: NotificationRecipientType.PhoneNumber, value: entry.value }
        )
    ))
  })
  )
)

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
      <RecipientSelector
        value={convertRecipientsToOptions(inProgressRule.recipients || [])}
        options={[]}
        onChange={createRecipientFieldUpdater(updateField)('recipients')}
      />
    </div>
  )

export default RuleModificationGeneral
