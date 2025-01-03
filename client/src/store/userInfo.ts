import { create } from 'zustand';

export interface IUserInfo {
  username: string;
  email: string;
  id: string;
  isLoggedIn: boolean;
}

export interface UserStore {
  userInfo: IUserInfo;
  setUserInfo: (userInfo: IUserInfo) => void;
  clearUserInfo: () => void;
}

const useUserStore = create<UserStore>((set) => ({
  userInfo: {
    username: '',
    email: '',
    id: '',
    isLoggedIn: false,
  },
  setUserInfo: (userInfo) => set({ userInfo: {...userInfo, isLoggedIn: true} }),
  clearUserInfo: () => set({ userInfo: { username: '', email: '', id: '', isLoggedIn: false } }),
}));

export default useUserStore;
