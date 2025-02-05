import {createStore} from 'zustand/vanilla'

export type UserState = {
  email?: string | null,
  id?: string | null,
  username?: string | null,
  active: boolean
}

export type UserActions = {
  updateUserInfo: (userInfo: UserState) => void,
  loginCheck: () => boolean,
}

export type UserStore = UserState & UserActions

export const initUserStore = (): UserState => {

  return { 
    email: null,
    id: null,
    username: null,
    active: false
  }
}

export const defaultInitState: UserState = {
  email: null,
  id: null,
  username: null,
  active: false
}

export const createUserStore = (
  initState: UserState = defaultInitState,
) => {
  return createStore<UserStore>()((set, get) => ({
    ...initState,
    updateUserInfo: (userInfo) => set(() => ({...userInfo})),
    loginCheck: () => get().active,
  }))
}