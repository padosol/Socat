import PostDetail from "@/components/socat/[id]/posts/PostDetail";
import { getPostById } from "@/lib/api/post/get-post-by-id"
import { notFound } from "next/navigation";

export default async function Page(props: {
  params: Promise<{
    id: string, 
    postId: string,
  }>,
}) {

  const params = await props.params
  const postId = params.postId;
  const roomId = params.id;
  
  const post = await getPostById(postId);

  if (post == null) {
    notFound();
  } 

  return (
    <div>
      <PostDetail post={post} roomId={roomId}/>
    </div>
  )
}