import React from 'react'
import { withTheme, WithTheme } from '@material-ui/core/styles'

export interface PredicateCounterProps {
  options: string[]
}

const PredicateCounter: React.SFC<PredicateCounterProps> = ({ options}) => { 
  return (
    <p>
      If   
      <select>
        {options.map((opt: string) => (
          <option> {opt} </option>
        ))}
      </select>
      of the following conditions are met:
    </p>
  )
}

// @ts-ignore
export default withTheme()(PredicateCounter)