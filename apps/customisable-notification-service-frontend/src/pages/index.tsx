import React from 'react'
import styled from 'styled-components'

import { Route, Switch, RouteComponentProps } from 'react-router'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import { pageDefinitions, LazyComponent } from './page-definitions'
import Fallback from '@/pages/Fallback'

const StyledPageWrapper = styled.div`
    flex-grow: 1;
`

const LazyPage = (Page: LazyComponent) => (props: any & RouteComponentProps) => (
  <React.Suspense fallback={<LoadingIndicator isCentral={true} />}>
    <Page {...props} parameters={props.match.params} />
  </React.Suspense>
)

const Pages = () => (
  <StyledPageWrapper>
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
  </StyledPageWrapper>
)

export default Pages
