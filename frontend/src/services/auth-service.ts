const authServiceUrl = process.env.REACT_APP_IS_DEVELOPMENT
                     ? 'http://localhost:3000' // Started with docker-container (BMW Mock)
                     : '' // TODO: Insert production url for auth service

const authenticationUrl = authServiceUrl + '/api/login'
const authenticationParameters: RequestInit = {
  method: 'POST',
  headers: {
    "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
  },
  mode: "cors",
  cache: "no-cache",
  // Test Parameters for provided BMW Mock.
  body: 'username=admin&password=fd123!'
}

const fetchAuthenticationToken = () => (
  fetch(authenticationUrl, authenticationParameters)
)

export {
  fetchAuthenticationToken
}
