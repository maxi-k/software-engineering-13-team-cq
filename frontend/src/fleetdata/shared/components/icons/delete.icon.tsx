import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <path
          d="M28,75.235C28,77.867,29.981,80,32.425,80h29.488C64.357,80,67,77.153,67,74.522V33H28V75.235z M32,37h31
		v37.521c-0.01,0.435-0.718,1.317-1.176,1.479h-29.4C32.279,76,32,75.702,32,75.235V37z"
        />
        <rect x="40" y="41" width="4" height="31" />
        <rect x="51" y="41" width="4" height="31" />
        <polygon points="28,17.379 28,21.238 67,28.115 67,24.256" />
      </g>
    </IconRenderer>
  );
};
