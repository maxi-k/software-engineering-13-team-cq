import * as React from 'react';
import { mediumgray } from 'src/shared/styles/variables';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props} viewbox="0 0 62 84" fill={mediumgray}>
      <g>
        <path
          d="M71,33H68V27a21,21,0,0,0-42,0v6H16V90H78V33ZM30,27a17,17,0,0,1,34,0v6H30ZM74,86H20V37H74Z"
          transform="translate(-16 -6)"
        />
        <path
          d="M53,56.16a6,6,0,1,0-7.86,5.74L41.72,75H52.28L48.83,61.9A6,6,0,0,0,53,56.16Z"
          transform="translate(-16 -6)"
        />
      </g>
    </IconRenderer>
  );
};
