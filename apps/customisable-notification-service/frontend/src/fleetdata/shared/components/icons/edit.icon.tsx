import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <g>
        <polygon points="73,74 23,74 23,22 53.617,22 56.973,18 19,18 19,78 77,78 77,36.915 73,41.682 	" />
        <path
          d="M38.495,47.954l-2.978,3.551l-3.568,17.708l16.82-6.588l2.979-3.55l0,0l29.649-35.334l-13.252-11.12
          L38.495,47.954L38.495,47.954z M68.613,17.974l7.43,6.235L48.837,56.633l-7.431-6.236L68.613,17.974z M46.338,59.496l-9.287,3.638
          l1.97-9.778L46.338,59.496z"
        />
      </g>
    </IconRenderer>
  );
};
