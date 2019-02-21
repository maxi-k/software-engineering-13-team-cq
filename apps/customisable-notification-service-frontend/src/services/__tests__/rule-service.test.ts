import fetchMock from 'fetch-mock'
import {
  fetchRuleOverview, mockedRuleOverview,
  fetchRuleDetail, mockedRuleDetail,
  createNewRule,
  convertFromAPIRule,
  convertToAPIRule
} from '../rule-service';

describe('The Rule fetch functions', () => {
  it('fetchRuleOverview fetches the rule overview', () => {
    fetchRuleOverview('authToken').then((result) => {
      expect(fetchMock.called(/notification-rule/)).toBe(true)
      expect(result).toBe(mockedRuleOverview)
    })
  })

  it('fetchRuleDetail fetches the rule detail for a rule', () => {
    fetchRuleDetail('authToken', 42).then((result) => {
      expect(fetchMock.called(/notification-rule\/42/)).toBe(true)
      expect(result).toBe(mockedRuleDetail)
    })
  })

  it('createNewRule creates a new notification rule', () => {
    createNewRule('authToken', mockedRuleDetail).then((result) => {
      expect(fetchMock.called(/notification-rule/)).toBe(true)
      expect(result).toBe(mockedRuleDetail)
    })
  })
})

describe('the conversion functions work', () => {
  it('convertFromApiRule and convertToApiRule are dual', () => {
    const mockedRule = mockedRuleDetail(3)
    expect(convertFromAPIRule(convertToAPIRule(mockedRule))).toEqual(mockedRule)
  })
})
