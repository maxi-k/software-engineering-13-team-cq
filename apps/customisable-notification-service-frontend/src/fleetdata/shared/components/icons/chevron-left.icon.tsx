import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer viewbox="0 0 109 209" {...props}>
      <g fill="none" stroke={props.fill} strokeWidth={6}>
        <polyline points="90,18 19,104.778 90,191.556 	" />
      </g>
    </IconRenderer>
  );
};
