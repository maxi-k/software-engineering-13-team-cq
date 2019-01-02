import React from 'react'
import styled from 'styled-components'
import Progress, { CircularProgressProps as ProgressProps } from '@material-ui/core/CircularProgress'
import { stripUnit } from 'polished'

export interface LoadingIndicatorProps extends ProgressProps {
  isCentral?: boolean
}

const CenteringWrapper = styled("div") <{ size: string }>`
    position: fixed;
    z-index: 999;
    height: ${props => stripUnit(props.size) + 'px'}
    width: ${props => stripUnit(props.size) + 'px'}
    overflow: show;
    margin: auto;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
`

const LoadingIndicator: React.SFC<LoadingIndicatorProps> = ({ isCentral, ...props }) => (
  isCentral ?
    <CenteringWrapper size={(props.size || 80) + ""}>
      <Progress {...{ size: 80, ...props }} />
    </CenteringWrapper>
    : <Progress {...props} />
)

export default LoadingIndicator
