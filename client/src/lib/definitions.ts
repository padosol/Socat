export type Room = {
  roomId: string;
  userId: string;
  roomName: string;
  roomDesc: string;
  roomType: string;
  createdAt: string;
};

export type User = {
  email: string,
  id: string,
  username: string,
}

export type ApiResponse<T> = {
  success: boolean,
  data: T,
  error: ErrorResponse
}

export type ErrorResponse = {
  timestamp: Date,
  status: number,
  error: string,
  message: Map<string, string>
}

export type Post = {
  roomId: string,
  postId: string,
  title: string,
  content: string,
  createdAt: string,
  updatedAt?: string | null,
  username: string,

}

export type PostSearch = {
  page: number,
  query: string,
}

export type TokenDTO = {
  accessToken: string,
  refreshToken: string
}

export type PostWithPage = {
  posts: Post[],
  total: number,
  pageNumber: number,
  pageSize: number,
}