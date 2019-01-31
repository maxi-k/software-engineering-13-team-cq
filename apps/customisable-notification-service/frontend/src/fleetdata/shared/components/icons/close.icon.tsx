import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => (
  <IconRenderer {...props} viewbox="0 0 49 49">
    <polygon
      points="40,11.213 37.787,9 24.5,22.287 11.213,9 9,11.213 22.287,24.5 9,37.787 11.213,40 24.5,26.713 
37.787,40 40,37.787 26.713,24.5 "
    />
  </IconRenderer>
);
