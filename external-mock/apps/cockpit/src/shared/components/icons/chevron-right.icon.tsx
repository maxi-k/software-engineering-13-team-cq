import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer viewbox="0 0 109 209" {...props}>
      <g fill="none" stroke={props.fill} strokeWidth={6}>
        <polyline points="19,18 90,104.778 19,191.556 	" />
      </g>
    </IconRenderer>
  );
};
