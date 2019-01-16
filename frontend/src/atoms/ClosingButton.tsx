import React from 'react'
import { IconProps } from '@fleetdata/shared/components/icons//icon.render.component';
import CloseIcon from '@fleetdata/shared/components/icons/close.icon'
import IconButton, { IconButtonProps } from '@material-ui/core/IconButton'

export type ClosingButtonProps = IconProps & IconButtonProps

const ClosingButton: React.SFC<ClosingButtonProps> = (
  { color, height, width, viewbox, fill, marginTop, ...props }) => (
    <IconButton color={color} {...props}>
      <CloseIcon
        color={color}
        height={height}
        width={width}
        viewbox={viewbox}
        fill={fill}
        marginTop={marginTop} />
    </IconButton>
  )

export default ClosingButton
