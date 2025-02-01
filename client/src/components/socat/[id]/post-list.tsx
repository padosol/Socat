
import getAllPostByRoomId from "@/lib/api/post/get-all-post-by-id"

export default async function PostList({
  id
}: {
  id: string
}) {
  const posts = await getAllPostByRoomId(id);


  return (
    <div>

    </div>
  )
}