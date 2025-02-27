"use server"

import getCommunityById from "@/lib/api/communities/get-community-id";
import { notFound } from "next/navigation";
import PostList from "@/components/socat/[id]/post-list";
import { AuthButton } from "@/components/button";
import Link from "next/link";
import { PostSkeleton } from "@/components/skeletons";
import { Suspense } from "react";

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
  const community = await getCommunityById(id);
  
  if (!community) {
    notFound();
  }
  
  const searchParams = await props.searchParams;
  const query = searchParams?.query || '';
  const currentPage = Number(searchParams?.page) || 1;

  return(
    <div className="p-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">{community.communityName}</h1>
        <div>
          <AuthButton >
            <Link href={`/socat/${id}/posts/create`}>
              게시글 작성
            </Link>
          </AuthButton>
        </div>
      </div>
      <div className="space-y-4">
        <Suspense key={query + currentPage} fallback={<PostSkeleton />}>
          <PostList communityId={id} query={query} currentPage={currentPage}/>
        </Suspense>
      </div>
    </div>
  )
}