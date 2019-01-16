import React from 'react'
import CloseIcon from '@fleetdata/shared/components/icons/close.icon'

interface ClosingButtonAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}

export type ClosingButtonProps = ClosingButtonAttributes & React.HTMLAttributes<HTMLDivElement>

const ClosingButton: React.SFC<ClosingButtonProps> = ({ onClick }) => (
  <div onClick = {onClick} style={{ cursor: 'pointer'}}>
  <CloseIcon/>
</div>
)

export default ClosingButton
