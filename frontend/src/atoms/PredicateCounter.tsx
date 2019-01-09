import React from 'react'
import { PredicateCounterValue } from '@/model/Rule'

export interface PredicateCounterProps {
  options: PredicateCounterValue[]
}

const PredicateCounter: React.SFC<PredicateCounterProps> = ({ options}) => { 
  return (
    <p>
      If   
      <select>
        {options.map((option: PredicateCounterValue) => (
          <option> {option} </option>
        ))}
      </select>
      of the following conditions are met:
    </p>
  )
}

// @ts-ignore
export default PredicateCounter