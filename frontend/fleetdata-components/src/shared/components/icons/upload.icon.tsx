import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <polygon points="71,61 71,75 25,75 25,61 21,61 21,79 75,79 75,61 	" />
        <polygon points="46,25.549 46,64 50,64 50,25.549 64.803,38.11 67.197,35.289 48,19 28.803,35.289 31.197,38.11 	" />
      </g>
    </IconRenderer>
  );
};
