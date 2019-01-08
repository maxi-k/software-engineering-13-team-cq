import { combineReducers, Store } from 'redux';
import { createStore, applyMiddleware, compose } from 'redux'
import thunk from 'redux-thunk'
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { StateType } from 'typesafe-actions';

import ruleReducer from './rule'
import languageReducer from './language'
import authReducer from './auth'

const enhancers = []
const middlewares = [
  thunk
]

const allReducers = {
  rule: ruleReducer,
  language: languageReducer,
  auth: authReducer
}

if (process.env.REACT_APP_IS_DEVELOPMENT) {
  // needs to be ignored, because typescript will give an error
  // for tye dynamic addition of the field on 'Window'
  // @ts-ignore
  const devToolsExtension = window.__REDUX_DEVTOOLS_EXTENSION__
  if (devToolsExtension !== null && typeof devToolsExtension === 'function') {
    enhancers.push(devToolsExtension())
  }
}

const rootReducer = combineReducers({
  ...allReducers
});
export type RootState = StateType<typeof rootReducer>;

const middleware = compose(
  applyMiddleware(...middlewares),
  ...enhancers
)

const persistedReducer = persistReducer({
  key: 'bmw-cns-store',
  storage
}, rootReducer)

const store: Store<RootState> = createStore(persistedReducer, middleware)
const persistor = persistStore(store);

export { store, persistor, rootReducer, persistedReducer };
export default { store, persistor }
