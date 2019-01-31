import { lazy } from 'react'

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
  ruleEdit: {
    title: "cns.page.ruleEdit.title",
    path: '/rule/:ruleId/edit',
    interpolatePath: (ruleId: string) => `/rule/${ruleId}/edit`,
    exact: true,
    component: lazy(() => import('@/pages/RuleEdit'))
  },
  ruleDetail: {
    title: "cns.page.ruleDetail.title",
    path: '/rule/:ruleId',
    interpolatePath: (ruleId: string) => `/rule/${ruleId}`,
    exact: true,
    component: lazy(() => import('@/pages/RuleDetail'))
  },
  ruleCreate: {
    title: "cns.page.ruleCreate.title",
    path: '/create',
    interpolatePath: () => '/create',
    exact: true,
    component: lazy(() => import('@/pages/RuleCreate'))
  }
}
export type PageDefinitions = typeof pageDefinitions

export const interpolatePagePath = (page: keyof PageDefinitions, ...args: string[]): string => {
  return pageDefinitions[page].interpolatePath(...args)
}

export default pageDefinitions
