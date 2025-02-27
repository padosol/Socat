import getAllPostByCommunityId from "@/lib/api/post/get-all-post-by-id";
import CurrentDate from "@/components/currentdate";
import Pagination from "../pagination";
import Link from "next/link";

export default async function PostList({
  communityId,
  query,
  currentPage
}: {
  communityId: string;
  query: string;
  currentPage: number;
}) {
  const defaultSearch = {query: query, page: currentPage};

  const response = await getAllPostByCommunityId(communityId, defaultSearch);

  const totalPages = response?.totalPages || 0;
  const total = response?.total || 0;

  return (
    <div className="container mx-auto p-4">
      <table className="min-w-full bg-white border border-gray-200">
        <thead className="">
          <tr>
            <th className="py-2 px-4 border-b">번호</th>
            {/* <th className="py-2 px-4 border-b">분류</th> */}
            <th className="py-2 px-4 border-b">제목</th>
            <th className="py-2 px-4 border-b">작성자</th>
            <th className="py-2 px-4 border-b">작성일</th>
            <th className="py-2 px-4 border-b">조회</th>
            <th className="py-2 px-4 border-b">추천</th>
          </tr>
        </thead>
        <tbody>
          {response?.posts && response.posts.map((post, index) => (
            <tr key={post.postId} className="hover:bg-gray-100 text-center">
              <td className="py-2 px-4 border-b">{total - ((currentPage - 1) * 10) - (index)}</td>
              {/* <td className="py-2 px-4 border-b">게임</td> */}
              <td className="py-2 px-4 border-b">
                <Link href={`/socat/${communityId}/posts/${post.postId}`}>
                  {post.title}
                </Link>
              </td>
              <td className="py-2 px-4 border-b">{post.username}</td>
              <td className="py-2 px-4 border-b"><CurrentDate date={post.createdAt} /></td>
              <td className="py-2 px-4 border-b">0</td>
              <td className="py-2 px-4 border-b">0</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="flex w-full justify-center items-center py-2">
        <Pagination totalPages={totalPages}/>
      </div>
    </div>
  )
}