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
  TBD: {
    title: "cns.page.TBD.title",
    path: '/TBD',
    interpolatePath: () => `/TBD`,
    exact: true,
    component: lazy(() => import('@/pages/TBD'))
  },
  ruleOverview: {
    title: "cns.page.ruleOverview.title",
    path: '/',
    interpolatePath: () => '/',
    exact: true,
    component: lazy(() => import('@/pages/RuleOverview'))
  },
  ruleDetailGeneral: {
    title: "cns.page.ruleDetailGeneral.title",
    path: '/rule/general/:ruleId',
    interpolatePath: (ruleId: string) => `/rule/general/${ruleId}`,
    exact: true,
    component: lazy(() => import('@/pages/RuleDetailGeneral'))
  },
  ruleDetailCondition: {
    title: "cns.page.DetailCondition.title",
    path: '/rule/condition/:ruleId',
    interpolatePath: (ruleId: string) => `/rule/condition/${ruleId}`,
    exact: true,
    component: lazy(() => import('@/pages/RuleDetailCondition'))
  }
}
export type PageDefinitions = typeof pageDefinitions

export const interpolatePagePath = (page: keyof PageDefinitions, ...args: string[]): string => {
  return pageDefinitions[page].interpolatePath(...args)
}

export default pageDefinitions
