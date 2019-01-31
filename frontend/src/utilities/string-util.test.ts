import { capitalizeString } from './string-util'

describe('capitalizeString works', () => {

  it('capitalizes a string', () => {
    expect(capitalizeString('hello world')).toBe('Hello world')
  })

  it('does not change digits', () => {
    expect(capitalizeString('0 confidence')).toBe('0 confidence')
  })

  it('handles empty strings', () => {
    expect(capitalizeString("")).toBe("")
  })

})
