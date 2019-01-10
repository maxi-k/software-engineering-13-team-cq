import React from 'react'
import { BMWButton as Button } from '@fleetdata/shared/components/button'

interface BackButtonAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void,
  size?: number 
}

export type BackButtonProps = BackButtonAttributes & React.HTMLAttributes<HTMLDivElement>;

const BackButton: React.SFC<BackButtonProps> = ({ onClick, size = 20 }) => (
  <Button
    onClick = {onClick}
    label = "Back"
    primary = "true"
    icon={<img style={{ width: `${size}px`, height: `${size}px` }} src="/assets/svg/chevron-left.svg" />}
    />
)

export default BackButton


