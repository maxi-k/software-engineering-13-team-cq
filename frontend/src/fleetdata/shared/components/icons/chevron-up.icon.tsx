import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer viewbox="0 0 24 24" {...props}>
      <polygon points="19.485,14.531 11.903,8.097 4.32,14.531 5.485,15.903 11.903,10.458 18.32,15.903 	" />
    </IconRenderer>
  );
};
