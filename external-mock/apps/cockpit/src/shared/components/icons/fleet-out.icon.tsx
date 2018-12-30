import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <polygon points="56,76 21,76 21,20 56,20 56,39 60,39 60,16 17,16 17,80 60,80 60,57 56,57 	" />
      <polygon points="67.1,33.55 64.354,35.879 72.942,46 38,46 38,50 72.942,50 64.354,60.121 67.1,62.45 79.361,48 	" />
    </IconRenderer>
  );
};
