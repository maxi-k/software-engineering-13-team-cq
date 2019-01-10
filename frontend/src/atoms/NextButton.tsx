import React from 'react'
import { BMWButton as Button } from '@fleetdata/shared/components/button'

interface NextButtonAttributes {
  onClick(event: React.SyntheticEvent<any, any>): void
}

export type NextButtonProps = NextButtonAttributes & React.HTMLAttributes<HTMLDivElement>

const NextButton: React.SFC<NextButtonProps> =  ({ onClick }) => (
  <Button
    onClick={onClick}
    label="Next"
    primary="true"
    icon={<img src="/assets/svg/chevron-right.svg" />}/> 
)

export default NextButton
