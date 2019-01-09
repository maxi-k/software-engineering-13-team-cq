import { combineReducers, Store } from 'redux';
import { createStore, applyMiddleware, compose } from 'redux'
import createSagaMiddleware from 'redux-saga'
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { StateType } from 'typesafe-actions';

import ruleReducer, { sagas as ruleSagas } from './rule'
import languageReducer from './language'
import authReducer, { sagas as authSagas } from './auth'

const enhancers = []
const sagaMiddleware = createSagaMiddleware()
const middlewares = [
  sagaMiddleware
]

const allReducers = {
  rule: ruleReducer,
  language: languageReducer,
  auth: authReducer
}

const allSagas = [
   ...ruleSagas,
  ...authSagas
]

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

allSagas.forEach((saga) => sagaMiddleware.run(saga))

export { store, persistor, rootReducer, persistedReducer };
export default { store, persistor }
