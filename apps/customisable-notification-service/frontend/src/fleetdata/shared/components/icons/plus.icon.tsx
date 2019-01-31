import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => (
  <IconRenderer {...props} viewbox="0 0 200 200">
    <polygon
      points="29,51 31,51 31,31 51,31 51,29 31,29 31,9 29,9 29,29 9,29 9,31 29,31  "
    />
  </IconRenderer>
);
