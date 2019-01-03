import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer viewbox="0 0 24 24" {...props}>
      <polygon points="19.485,9.469 11.902,15.903 4.32,9.469 5.485,8.097 11.902,13.542 18.32,8.097 	" />
    </IconRenderer>
  );
};
