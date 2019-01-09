import { combineReducers, Store } from 'redux';
import { createStore, applyMiddleware, compose } from 'redux'
import createSagaMiddleware from 'redux-saga'
import { persistStore, persistReducer } from 'redux-persist'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import storage from 'redux-persist/lib/storage'
import { createBrowserHistory } from 'history'
import { StateType } from 'typesafe-actions';

import ruleReducer, { sagas as ruleSagas } from './rule'
import languageReducer from './language'
import authReducer, { sagas as authSagas } from './auth'

export const history = createBrowserHistory()

const sagaMiddleware = createSagaMiddleware()
const middlewares = [
  sagaMiddleware,
  routerMiddleware(history)
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

const composeEnhancers =
  typeof window === 'object' &&
  // @ts-ignore
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
  // @ts-ignore
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
    // Specify extension’s options like name, actionsBlacklist, actionsCreators, serialize...
  }) : compose;

const rootReducer = combineReducers({
  router: connectRouter(history),
  ...allReducers
});
export type RootState = StateType<typeof rootReducer>;

const middleware = composeEnhancers(
  applyMiddleware(...middlewares)
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
