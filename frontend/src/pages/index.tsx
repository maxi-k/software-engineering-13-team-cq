import React from 'react'
import { Route, Switch, RouteComponentProps } from 'react-router'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import { pageDefinitions, LazyComponent } from './page-definitions'
import Fallback from '@/pages/Fallback'

const LazyPage = (Page: LazyComponent) => (props: any & RouteComponentProps) => (
  <React.Suspense fallback={<LoadingIndicator isCentral={true} />}>
    <Page {...props} parameters={props.match.params} />
  </React.Suspense>
)

const Pages = () => (
  <Switch>
    {Object.keys(pageDefinitions).map((def) => {
      const page = pageDefinitions[def]
      return <Route key={def}
        path={page.path}
        exact={page.exact}
        component={LazyPage(page.component)} />
    })}
    <Route path="*" component={Fallback} />
  </Switch>
)

export default Pages
