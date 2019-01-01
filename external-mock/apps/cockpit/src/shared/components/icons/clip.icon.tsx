import * as React from 'react';
import { IconProps, IconRenderer } from './icon.render.component';

export default (props: IconProps) => {
  return (
    <IconRenderer {...props}>
      <path
        d="M33.988,79.469c-3.381,0-6.779-1.141-9.565-3.479c-6.293-5.281-7.118-14.698-1.837-20.992l30.211-36.004
		c1.872-2.23,4.5-3.599,7.4-3.853c2.897-0.253,5.726,0.637,7.956,2.509c4.604,3.863,5.207,10.752,1.344,15.356
		c-0.321,0.383-25.39,30.259-25.39,30.259c-2.622,3.126-7.299,3.537-10.425,0.913c-1.514-1.271-2.443-3.055-2.615-5.024
		c-0.172-1.969,0.433-3.888,1.703-5.401l20.194-23.977l2.906,2.448L35.678,56.198c-0.616,0.734-0.91,1.667-0.826,2.625
		s0.536,1.826,1.272,2.444c1.521,1.275,3.796,1.077,5.072-0.444c0,0,25.068-29.875,25.39-30.258c1.22-1.453,1.8-3.294,1.634-5.183
		c-0.165-1.889-1.056-3.601-2.509-4.82c-1.453-1.219-3.295-1.798-5.183-1.634c-1.889,0.165-3.601,1.056-4.82,2.509L25.497,57.44
		c-3.934,4.689-3.32,11.704,1.368,15.638c4.689,3.935,11.705,3.321,15.638-1.368l30.854-36.77l2.911,2.442l-30.854,36.77
		C42.472,77.66,38.244,79.469,33.988,79.469z"
      />
    </IconRenderer>
  );
};