import React, { lazy } from 'react'
import { Route } from 'react-router'
import LoadingIndicator from '@/atoms/LoadingIndicator'

type LazyComponent = React.LazyExoticComponent<any>

interface PageDefinition {
  title: string,
  path: string,
  component: LazyComponent
}

const pageDefinitions: { [key: string]: PageDefinition } = {
  ruleOverview: {
    title: "cns.page.ruleOverview.title",
    path: '/',
    component: lazy(() => import('@/pages/RuleOverview'))
  }
}

const WrapPage: React.SFC<{ Page: LazyComponent, path: string }> = ({ path, Page }) => (
  <Route path={path}>
    <React.Suspense fallback={<LoadingIndicator isCentral={true} />}>
      <Page />
    </React.Suspense>
  </Route>
)

const Pages: React.SFC<JSX.IntrinsicAttributes> = () => (
  <>
    {Object.keys(pageDefinitions).map((def) => {
      const page = pageDefinitions[def]
      return <WrapPage key={def} path={page.path} Page={page.component} />
    })}
  </>
)

export default Pages
