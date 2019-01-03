import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => (
  <IconRenderer viewbox="0 0 64 64" {...props}>
    <g stroke="none" strokeWidth="1">
      <g>
        <rect x="30" y="14" width="4" height="25" />
        <rect x="30" y="45" width="4" height="5" />
      </g>
    </g>
  </IconRenderer>
);
