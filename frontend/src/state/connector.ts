import { connect as reactConnect,
         Connect,
         MapStateToProps,
         MapDispatchToPropsFunction } from 'react-redux'
import { RootState } from '@/state'

export type StateMapper<ComponentProps, StoreProps> = MapStateToProps<StoreProps, ComponentProps, RootState>
export type DispatchMapper<ComponentProps, DispatchProps> = MapDispatchToPropsFunction<DispatchProps, ComponentProps>
export type StateType = RootState

export const connect: Connect = reactConnect
