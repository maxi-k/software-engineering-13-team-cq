import { lazy,  } from 'react'

export type LazyComponent = React.LazyExoticComponent<any>
export interface PageDefinition {
  title: string,
  path: string,
  component: LazyComponent,
  exact: boolean
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
  ruleDetail: {
    title: "cns.page.ruleDetail.title",
    path: '/rule/:ruleId',
    interpolatePath: (ruleId: string) => `/rule/${ruleId}`,
    exact: true,
    component: lazy(() => import('@/pages/RuleDetail'))
  }
}
export type PageDefinitions = typeof pageDefinitions

export const interpolatePagePath = (page: keyof PageDefinitions, ...args: string[]): string => {
    return pageDefinitions[page].interpolatePath(...args)
}

export default pageDefinitions
