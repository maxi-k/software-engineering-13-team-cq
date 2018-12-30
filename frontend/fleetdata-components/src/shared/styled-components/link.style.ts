import { rem } from 'polished';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { blue, fontL, fontXXL, lightblue } from '../styles/variables';

const baseLinkStyles = `color: ${lightblue};
text-decoration: underline;
&:hover,
&:focus,
&:visited {
  text-decoration: underline;
  color: ${blue};
}`;

export const StyledAnchorLink = styled.a`
  ${baseLinkStyles};
`;
export const StyledLink = styled(Link)`
  ${baseLinkStyles};
`;

export const NoStyleLink = styled(Link)`
  text-decoration: none;
`;

export const StyledH1 = styled.h1`
  color: ${lightblue};
  font-size: ${fontXXL};
  margin-bottom: 0;
`;

export const StyledH2 = styled.h2`
  font-size: ${fontL};
  font-weight: normal;
  padding-bottom: ${rem(32)};
`;
