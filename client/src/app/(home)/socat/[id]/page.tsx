import getRoomById from "@/lib/api/rooms/get-room-id";
import { notFound } from "next/navigation";
import PostList from "@/components/socat/[id]/post-list";
import { Button } from "@/components/button";
import { AuthButton } from "@/components/button";
import Link from "next/link";

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
          <AuthButton >
            <Link href={`/socat/${id}/posts/create`}>
              게시글 작성
            </Link>
          </AuthButton>
        </div>
      </div>
      <div className="space-y-4">
        <PostList roomId={id} />
      </div>
    </div>
  )
}