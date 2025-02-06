"use client"

import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // 기본 스타일
import { useRef } from "react";
import { useRouter } from "next/navigation";

export default function Form({
  roomId
}: {roomId: string}) {
  const router = useRouter();

  const editorRef = useRef<Editor>(null);

  const handleCancleButtonClick = () => {
    router.push(`/socat/${roomId}`)
  }

  const handleImageUpload = async (blob: File, callback: (url: string) => void) => {
    console.log(blob)

    callback("test")
  }

  return (
    <div className="p-4 flex justify-center">
      <form className="text-center">
        <div className="w-full">
          <input className="w-[750px] h-[40px] border mb-2 p-2" placeholder="제목"/>
        </div>
        <div className="bg-white h-[500px] w-[750px] text-left">
          <Editor
            ref={editorRef}
            height="100%"
            initialEditType="wysiwyg"
            previewStyle='vertical'
            hooks={{addImageBlobHook: handleImageUpload}}

          />
        </div>
        <button 
          onClick={handleCancleButtonClick}
          type="button" 
          className="mt-2 px-4 py-2 bg-gray-500 text-white rounded mr-2 hover:bg-gray-400"
        >
          취소
        </button>
        <button type="submit" className="mt-2 px-4 py-2 bg-blue-500 text-white hover:bg-blue-400 rounded">
          저장하기
        </button>
      </form>
    </div>
  )
}