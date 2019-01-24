import { authApiRequest, mockRequest, doMock } from '@/services/api-service'

const predicateFieldUrl = '/predicate-fields'

export const fetchPredicateFields = (accessToken: string) => (
  doMock
    ? mockRequest(predicateFieldUrl, require('./mocks/mockedPredicateFields.json'))
    : authApiRequest(predicateFieldUrl, accessToken)
)
