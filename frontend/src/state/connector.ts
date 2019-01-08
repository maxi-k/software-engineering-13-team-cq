import { connect as reactConnect,
         Connect,
         MapStateToProps,
         MapDispatchToPropsFunction } from 'react-redux'
import { RootState } from '@/state'

export type StateMapper<OP, SP> = MapStateToProps<SP, OP, RootState>
export type DispatchMapper<OP, DP> = MapDispatchToPropsFunction<DP, OP>
export type StateType = RootState

export const connect: Connect = reactConnect
