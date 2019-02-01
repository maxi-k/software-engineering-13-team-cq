import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <polygon points="20.944,36.823 8,25.961 9.921,23.671 20.576,32.611 38.709,11 41,12.922 	" />
    </IconRenderer>
  );
};
