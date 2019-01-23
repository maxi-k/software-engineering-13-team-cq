import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <polygon points="52,68 52,36 48.248,36 39.683,43.495 42.317,46.505 48,41.533 48,68 	" />
        <polygon points="71,22 71,26 75,26 75,75 21,75 21,26 25,26 25,22 17,22 17,79 79,79 79,22 	" />
        <rect x="33" y="22" width="30" height="4" />
        <rect x="27" y="17" width="4" height="14" />
        <rect x="65" y="17" width="4" height="14" />
      </g>
    </IconRenderer>
  );
};
