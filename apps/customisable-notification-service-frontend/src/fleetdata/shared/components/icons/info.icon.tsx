import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => (
  <IconRenderer viewbox="5 5 38 38" {...props}>
    <g>
      <path
        d="M24.5,8C15.387,8,8,15.387,8,24.5C8,33.613,15.387,41,24.5,41S41,33.613,41,24.5C41,15.387,33.613,8,24.5,8z
		 M24.5,38.5c-7.72,0-14-6.28-14-14s6.28-14,14-14s14,6.28,14,14S32.22,38.5,24.5,38.5z"
      />
      <rect x="23" y="22" width="3" height="11" />
      <rect x="23" y="16" width="3" height="3" />
    </g>
  </IconRenderer>
);
