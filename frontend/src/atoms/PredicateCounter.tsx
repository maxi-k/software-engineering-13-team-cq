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
        {options.map((predicateCounterOption: PredicateCounterValue) => (
          <option> {predicateCounterOption} </option> // e.g. All, One, None
        ))}
      </select>
      of the following conditions are met:
    </p>
  )
}

export default PredicateCounter
