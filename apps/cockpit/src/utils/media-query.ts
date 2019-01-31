import { em } from 'polished';
import { css } from 'styled-components';

export const sizes: IMediaQuery = {
  xs: 0,
  sm: 600,
  md: 1025,
  lg: 1281,
  xl: 1441,
  xxl: 1921,
};

export interface IMediaQuery {
  xxl: any;
  xl: any;
  lg: any;
  md: any;
  sm: any;
  xs: any;
}

export const media = Object.keys(sizes).reduce(
  (acc: IMediaQuery, label) => {
    acc[label] = (...args: string[]) => css`
      @media (max-width: ${em(sizes[label])}) {
        ${css.call(undefined, ...args)};
      }
    `;
    return acc;
  },
  {} as IMediaQuery,
);
