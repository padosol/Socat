import getAllPostByRoomId from "@/lib/api/post/get-all-post-by-id";

export default async function PostList({
  roomId
}: {
  roomId: string
}) {
  const defaultSearch = {page: 0, size: 10, query: ""};

  const posts = await getAllPostByRoomId(roomId, defaultSearch);

  return (
    <div className="container mx-auto p-4">
      <table className="min-w-full bg-white border border-gray-200">
        <thead className="">
          <tr>
            <th className="py-2 px-4 border-b">NO</th>
            <th className="py-2 px-4 border-b">제목</th>
            <th className="py-2 px-4 border-b">작성자</th>
            <th className="py-2 px-4 border-b">작성일</th>
          </tr>
        </thead>
        <tbody>
          {posts.map(post => (
            <tr key={post.postId} className="hover:bg-gray-100">
              <td className="py-2 px-4 border-b">{post.postId}</td>
              <td className="py-2 px-4 border-b">{post.title}</td>
              <td className="py-2 px-4 border-b">{post.content}</td>
              <td className="py-2 px-4 border-b">{post.createdAt}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}