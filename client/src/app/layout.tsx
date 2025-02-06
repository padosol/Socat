import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { UserStoreProvider } from "@/stores/userInfo-store-provider";
import { type UserState } from "@/stores/userInfo-store";
import { cookies } from 'next/headers'
import { authenticate, refresh } from "@/lib/api/auth/auth";
import { getUserInfoByToken } from "@/lib/api/auth/login";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Socat",
  description: "나만의 커뮤니티를 만드세요.",
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  // 여기서 유저 정보 가져오는 로직 구현현
  const cookieStore = await cookies();
  const accessToken = cookieStore.get("accessToken");

  const userState: UserState = {
    email: null,
    id: null,
    username: null,
    active: false,
  }

  // access token 이 존재하면 유저정보 가져옴
  if (accessToken) {
    const response = await authenticate();

    let flag = true;
    if (!response) {
      const refresh_response = await refresh();
      console.log(refresh_response)

      if (!refresh_response) {
        flag = false;
      }
    } 

    if (flag) {
      const user_response = await getUserInfoByToken();
  
      userState.username = user_response.username;
      userState.id = user_response.id;
      userState.email = user_response.email;
      userState.active = true;
    }

  }

  return (
    <html lang="en">
      <body className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
        <UserStoreProvider initialUser={userState}>
          {children}
        </UserStoreProvider>
      </body>
    </html>
  );
}
