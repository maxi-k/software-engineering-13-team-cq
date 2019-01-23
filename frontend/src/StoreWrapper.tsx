import React from 'react'
import { Provider } from 'react-redux'
import { store, persistor, history } from './state'
import { PersistGate } from 'redux-persist/integration/react'
import { ConnectedRouter as Router } from 'connected-react-router';
import LoadingIndicator from '@/atoms/LoadingIndicator'

const StoreWrapper: React.SFC<JSX.IntrinsicAttributes> = ({ children }) => (
  <Provider store={store}>
    <PersistGate loading={<LoadingIndicator isCentral={true} />} persistor={persistor}>
      <Router history={history}>
        {children}
      </Router>
    </PersistGate>
  </Provider>
)

export default StoreWrapper
