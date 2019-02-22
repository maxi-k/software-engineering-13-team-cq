import React from 'react'
import styled from 'styled-components'

export interface FlexBarAttributes {
  padded?: boolean
}

export type FlexBarProps = FlexBarAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledFlexBar = styled.div<FlexBarProps>`
  display: flex;
  ${(props: FlexBarProps) => props.padded ? 'padding: 1rem' : ''}
  align-items: center;
  justify-content: space-between;
`
export default StyledFlexBar
