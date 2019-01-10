const apiUrl = process.env.REACT_APP_IS_DEVELOPMENT
             ? 'http://localhost:3000'
             : 'st-calculator-backend.herokuapp.com'

const defaultOptions = {
  headers: {
    'Content-Type': 'application/json'
  }
}

const apiRequest = (path: string, options: object = {}) => {
  return fetch(path.startsWith('/')
             ? apiUrl + path
             : apiUrl + '/' + path
             , {...defaultOptions, ...options}
  )
}

const doMock = process.env.REACT_APP_IS_DEVELOPMENT
const delayResponse = (min: number, max: number) => (
  new Promise(resolve => {
    setTimeout(resolve, Math.random() * (max - min) + min)
  })
)

const mockRequest = (path: string, response: object, fetchOptions: object = {}) => {
  const fetchMock = require('fetch-mock')
  fetchMock.mock(`end:${path}`, delayResponse(50, 200).then(() => response))
  // We want a console print here, as there will be no information in
  // the network tab, and this is development only.
  // tslint:disable-next-line:no-console
  console.log('Mocking API Request to: ' + path);
  const promise = apiRequest(path, fetchOptions)
  fetchMock.restore()
  return promise
}

export {
  apiUrl,
  apiRequest,
  doMock,
  mockRequest,
}
