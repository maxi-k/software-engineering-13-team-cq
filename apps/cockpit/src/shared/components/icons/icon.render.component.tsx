import * as React from 'react';
import { lightblue } from 'src/shared/styles/variables';

export interface IconProps {
  className?: string;
  color?: string;
  height?: number;
  width?: number;
  viewbox?: string;
  fill?: string;
  marginTop?: number;
}

export class IconRenderer extends React.Component<IconProps> {
  public height = this.props.height ? this.props.height : 96;
  public width = this.props.width ? this.props.width : 96;

  public render() {
    return (
      <svg
        version="1.1"
        id="default"
        width="100%"
        height="100%"
        className={this.props.className}
        viewBox={this.props.viewbox || '0 0 96 96'}
        style={{
          width: `${this.width}px`,
          height: `${this.height}px`,
          minWidth: `${this.props.width}px`,
          fill: this.props.color || this.props.fill || lightblue,
          marginTop: this.props.marginTop,
        }}
      >
        {this.props.children}
      </svg>
    );
  }
}
