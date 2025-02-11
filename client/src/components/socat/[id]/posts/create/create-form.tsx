"use client"

import { Editor } from "@toast-ui/react-editor";
import { FormEventHandler, useRef } from "react";
import { useRouter } from "next/navigation";
import { uploadFile } from "@/lib/api/post/upload";
import { createPost } from "@/lib/api/post/create.post";
import TuiEditor from "./tui-editor";

export default function Form({
  roomId
}: {roomId: string}) {
  const router = useRouter();

  const editorRef = useRef<Editor>(null);

  const handleCancleButtonClick = () => {
    router.push(`/socat/${roomId}`)
  }

  const handleImageUpload = async (blob: File, callback: (url: string) => void) => {
    const formData = new FormData();
    formData.append("file", blob)

    const response = await uploadFile(formData)

    callback(`https://d25hmvjcsah1ye.cloudfront.net/${response}`)
  }

  const onSubmit: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    const form = event.currentTarget
    const formData = new FormData(form)

    const title = formData.get("title") as string
    const content = editorRef.current?.getInstance().getHTML();

    try {
      const response = await createPost({
        roomId, title, content
      });

      router.push(`/socat/${roomId}`)

    } catch(e) {
      console.log(e)
    }

    // if (response.data.success) {
    //   revalidatePath(`/socat/${roomId}`)
    //   redirect(`/socat/${roomId}`)
    // }
  }

  return (
    <div className="p-4 flex justify-center">
      <form className="text-center" onSubmit={onSubmit}>
        <div className="w-full">
          <input className="w-[750px] h-[40px] border mb-2 p-2" placeholder="제목" name="title"/>
        </div>
        <div className="bg-white h-[500px] w-[750px] text-left">
          <TuiEditor
            ref={editorRef}
            height="100%"
            previewStyle='vertical'
            hooks={{addImageBlobHook: handleImageUpload}}
            initialValue="내용을 입력해주세요."
            initialEditType="WYSIWYG"
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