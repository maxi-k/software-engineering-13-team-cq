import * as React from 'react';

import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <path d="M22,79.451V16.558l55.031,31.366L22,79.451z M26,23.442v49.107l42.969-24.617L26,23.442z" />
      </g>
    </IconRenderer>
  );
};
