import React from 'react'
import PlusIcon from '@fleetdata/shared/components/icons/plus.icon'

interface AddConditionButtonAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}

export type AddConditionButtonProps = AddConditionButtonAttributes & React.HTMLAttributes<HTMLDivElement>

const AddConditionButton: React.SFC<AddConditionButtonProps> = ({ onClick }) => (
  <div onClick = {onClick} style={{ cursor: 'pointer', width: '50px'}}>
  <PlusIcon/>
</div>
)

export default AddConditionButton
