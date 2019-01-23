import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <polygon points="71,61 71,75 25,75 25,61 21,61 21,79 75,79 75,61 " />
      <polygon points="64.803,44.589 50,57.15 50,18 46,18 46,57.15 31.197,44.589 28.803,47.411 48,63.699 67.197,47.411 " />
    </IconRenderer>
  );
};
