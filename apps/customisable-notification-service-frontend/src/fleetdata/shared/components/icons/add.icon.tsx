import * as React from 'react';

import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props} viewbox="0 0 83.2 83.2">
      <polygon
        points="43.6,39.6 43.6,10.6 39.6,10.6 39.6,39.6 10.6,39.6 10.6,43.6 39.6,43.6 39.6,72.6 43.6,72.6 
        43.6,43.6 72.6,43.6 72.6,39.6"
      />
    </IconRenderer>
  );
};
