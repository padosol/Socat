import getRoomById from "@/lib/api/rooms/get-room-id";
import { notFound } from "next/navigation";
import PostList from "@/components/socat/[id]/post-list";

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
      <h1 className="text-2xl font-bold mb-4">{socat.roomName}</h1>
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