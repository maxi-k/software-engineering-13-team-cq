import React from 'react'
import styled from 'styled-components'
import { Link } from 'react-router-dom'
import { interpolatePagePath } from '@/pages/page-definitions'

export interface TBDPageAttributes {
  ruleId: string // needs to be a string because it's passed down by router
}

export type TBDPageProps = TBDPageAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledTBDPage = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
`

const TBDPage: React.SFC<TBDPageProps> = ({ ruleId, ...props }) => (
  <StyledTBDPage {...props}>
    To be implemented <br />
    <Link to={interpolatePagePath('ruleOverview', ruleId)}>
      Back to Rule View
    </Link>
  </StyledTBDPage>
)

export default TBDPage
