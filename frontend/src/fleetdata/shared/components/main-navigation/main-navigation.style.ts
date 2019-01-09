import { NavLink } from 'react-router-dom';
import styled, { keyframes } from 'styled-components';

export const StyledNavUl = styled.ul`
  background: #fff;
  transition: 0.35s all ease;
  position: relative;
  white-space: nowrap;
  font-size: 0;
  min-height: 32px;
  transition: margin-top 0.15s ease-in-out;
  padding-inline-start: 0;

  &.collapsed {
    margin-top: -32px;
    transition: margin-top 0.15s ease-in 0.1s;

    & li a span {
      transition: opacity 0.1s ease-in-out;
      opacity: 0;
    }
  }
`;

export const StyledNavLi = styled.li`
  border-left: 2px solid #fff;
  border-right: 2px solid #fff;
  margin-bottom: -5px;
  display: inline-block;
`;

export const borderanimationHover = keyframes`
0.00%{padding-bottom:15px;border-bottom-width:5px}
100.00%{padding-bottom:12px;border-bottom-width:8px}
`;

export const borderanimation = keyframes`
0.00%{padding-bottom:12px;border-bottom-width:8px}
100.00%{padding-bottom:15px;border-bottom-width:5px
`;

export const StyledNavLink = styled(NavLink).attrs({
  color: (props: { color?: string }) => props.color || '#b2b2b2',
})`
  padding: 15px 28px;
  font-size: 15px;
  border-bottom: 5px solid;
  border-color: ${(props: { color: string }) => props.color};
  transition: 0.35s all ease;
  padding: 15px 28px;
  display: inline-block;
  font-family: 'BMWGroupCondensed', helvetica, sans-serif;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 0.3px;
  line-height: 15px;
  color: #444;
  text-decoration: none;
  transition: 0.35s all ease;
  animation: ${borderanimation} 0.15s 0s ease-out forwards;

  & span {
    transition: opacity 0.1s ease-out 0.2s;
  }

  &:hover,
  &.selected {
    animation: ${borderanimationHover} 0.15s 0s ease-out forwards;
  }
  &:hover {
    color: #000;
  }
`;
