import { lazy,  } from 'react'

export type LazyComponent = React.LazyExoticComponent<any>
export interface PageDefinition {
  title: string,
  path: string,
  component: LazyComponent,
  exact: boolean,
  interpolatePath(...values: string[]): string
}

export const pageDefinitions: { [key: string]: PageDefinition } = {
  ruleOverview: {
    title: "cns.page.ruleOverview.title",
    path: '/',
    interpolatePath: () => '/',
    exact: true,
    component: lazy(() => import('@/pages/RuleOverview'))
  },
  TBD: {
    title: "cns.page.TBD.title",
    path: '/TBD',
    interpolatePath: (ruleId: string) => `/TBD`,
    exact: true,
    component: lazy(() => import('@/pages/TBD'))
  },
  ruleDetailGeneral: {
    title: "cns.page.ruleDetailGeneral.title",
    path: '/rule/general/:ruleId',
    interpolatePath: (ruleId: string) => `/rule/general/${ruleId}`,
    exact: true,
    component: lazy(() => import('@/pages/RuleDetailGeneral'))
  }
}
export type PageDefinitions = typeof pageDefinitions

export const interpolatePagePath = (page: keyof PageDefinitions, ...args: string[]): string => {
  return pageDefinitions[page].interpolatePath(...args)
}

export default pageDefinitions
