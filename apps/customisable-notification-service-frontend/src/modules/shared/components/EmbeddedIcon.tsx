import React from 'react'
import { withTheme, WithTheme } from '@material-ui/core/styles'
import { IconProps } from '@fleetdata/shared/components/icons/icon.render.component'

interface EmbeddedIconAttributes {
  component(props: IconProps): React.ReactElement<IconProps>
}

export type EmbeddedIconProps = EmbeddedIconAttributes & IconProps

const EmbeddedIcon: React.SFC<EmbeddedIconProps & WithTheme> = ({ theme, component, ...iconProps }) => {
  const Component = component
  return <Component width={24} height={24} fill={theme.palette.text.toString()} {...iconProps} />
}

export default withTheme()(EmbeddedIcon)
