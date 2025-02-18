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
  roomId
}: {
  post: Post,
  roomId: string
}) {

  return (
    <div className="border">
      <div>
        <div>
          <span>제목: {post.title}</span>
          <div>
            <span>
              {post.createdAt}
            </span>
            <span>조회수 0</span>
            <span>댓글 0</span>
            <span>추천 0</span>
          </div>
        </div>
        <EditorViewer initialValue={post.content}/>
      </div>

      <div className="flex">
        <Button>Good</Button>
        <Button>Woops</Button>
      </div>

      <div>
        <Link href={`/socat/${roomId}`}>
          <Button>뒤로가기</Button>
        </Link>
      </div>
    </div>
  )
}