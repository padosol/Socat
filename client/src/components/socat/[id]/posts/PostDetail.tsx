"use client"

import { Post } from "@/lib/definitions"
import dynamic from "next/dynamic"
import { Button } from "@/components/button"
import Link from "next/link"

const EditorViewer = dynamic(() => import('./editor-viewer'), {
  ssr: false,
})

export default function PostDetail({
  post,
  communityId
}: {
  post: Post,
  communityId: string
}) {

  return (
    <div className="border p-4 rounded-md shadow-md">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">{post.title}</h1>
        <div className="text-gray-500 text-sm flex justify-between">
          <div>
            <span>일반</span>
            <span className="mx-2">|</span>
            <span>{new Date(post.createdAt).toLocaleDateString()}</span>
            <span className="mx-2">|</span>
            <span className="">{post.username}</span>
          </div>
          <div>
            <span className="ml-4">조회수 0</span>
            <span className="ml-4">댓글 0</span>
            <span className="ml-4">추천 0</span>
          </div>
        </div>
      </div>
      <div className="mb-4 min-h-[200px]">
        <EditorViewer initialValue={post.content}/>
      </div>
      <div className="flex space-x-2 mb-4 justify-center">
        <Button className="bg-green-500 text-white">Good</Button>
        <Button className="bg-red-500 text-white">Woops</Button>
      </div>
      <div className="flex justify-end">
          <Button className="bg-gray-500 text-white">
            <Link href={`/socat/${communityId}`}>
              뒤로가기
            </Link>
          </Button>
      </div>
    </div>
  )
}