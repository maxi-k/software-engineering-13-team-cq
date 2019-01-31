import { isDevelopment, isTest } from './environment-service'

const apiUrl = isDevelopment
  ? 'http://localhost:3000'
  : process.env.REACT_APP_BACKEND_URL || ''

const defaultOptions = {
  headers: {
    'Content-Type': 'application/json'
  }
}

const apiRequest = (path: string, options: object = {}) => {
  return fetch(path.startsWith('/')
    ? apiUrl + path
    : apiUrl + '/' + path
    , { ...defaultOptions, ...options }
  )
}

const authApiRequest = (path: string, authToken: string, options: object = {}) => {
  const optionsWithAuthHeaders = {
    headers: {
      ...defaultOptions.headers,
      'Authorization': 'Bearer ' + authToken
    },
    ...options
  }
  return apiRequest(path, optionsWithAuthHeaders)
}

const doMock = isTest
const delayResponse = (min: number, max: number) => (
  new Promise(resolve => {
    setTimeout(resolve, Math.random() * (max - min) + min)
  })
)

// response object:
// see http://www.wheresrhys.co.uk/fetch-mock/#api-mockingmock_response
const mockRequest = (path: string, response: object, fetchOptions: object = {}) => {
  const fetchMock = require('fetch-mock')
  fetchMock.mock(`end:${path}`, delayResponse(50, 200).then(() => response))
  // We want a console print here, as there will be no information in
  // the network tab, and this is development only.
  // tslint:disable-next-line:no-console
  console.info('Mocking API Request to: ' + path);
  const promise = apiRequest(path, fetchOptions)
  fetchMock.restore()
  return promise
}

export {
  apiUrl,
  apiRequest,
  authApiRequest,
  doMock,
  mockRequest,
}
