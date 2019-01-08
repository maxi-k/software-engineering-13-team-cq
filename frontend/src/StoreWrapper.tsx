import React from 'react'
import { Provider } from 'react-redux'
import { store, persistor } from './state'
import { PersistGate } from 'redux-persist/integration/react'
import { BrowserRouter as Router } from 'react-router-dom';
import LoadingIndicator from '@/atoms/LoadingIndicator'

const StoreWrapper: React.SFC<JSX.IntrinsicAttributes> = ({ children }) => (
  <Provider store={store}>
    <PersistGate loading={<LoadingIndicator isCentral={true} />} persistor={persistor}>
      <Router>
        {children}
      </Router>
    </PersistGate>
  </Provider>

)

export default StoreWrapper
