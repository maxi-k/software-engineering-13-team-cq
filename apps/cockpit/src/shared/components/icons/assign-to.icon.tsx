import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <polygon points="33,16 33,39 37,39 37,20 72,20 72,76 37,76 37,57 33,57 33,80 76,80 76,16 	" />
        <polygon points="46.354,60.121 49.1,62.45 61.361,48 49.1,33.55 46.354,35.879 54.942,46 20,46 20,50 54.942,50 	" />
      </g>
    </IconRenderer>
  );
};
