import getRoomById from "@/lib/api/rooms/get-room-id";
import { notFound } from "next/navigation";
import PostList from "@/components/socat/[id]/post-list";
import { Button } from "@/components/button";

export default async function Page(props: {
  params: Promise<{ 
    id: string,
  }>,
  searchParams: Promise<{
    query: string,
    page: string,
  }>
}) {
  
  const params = await props.params;
  const id = params.id;
  const socat = await getRoomById(id);
  
  if (!socat) {
    notFound();
  }
  
  const searchParams = await props.searchParams;
  const query = searchParams?.query || '';
  const currentPage = Number(searchParams?.page) || 1;

  console.log(socat)

  return(
    <div className="p-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">{socat.roomName}</h1>
        <div>
          <Button>
            게시글 작성
          </Button>
        </div>
      </div>
      <div className="space-y-4">
        <PostList id={id} />
        {/* {relatedPosts.map((post) => (
          <div key={post.id} className="bg-white p-4 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold">{post.title}</h2>
            <p className="text-gray-700">{post.content}</p>
          </div>
        ))} */}
      </div>
    </div>
  )
}