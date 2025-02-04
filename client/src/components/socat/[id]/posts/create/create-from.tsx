"use client"

import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // 기본 스타일
import "@toast-ui/editor/dist/toastui-editor-viewer.css"; // 뷰어 스타일
import { useRef } from "react";

export default function Form({
  roomId
}: {roomId: string}) {

  const editorRef = useRef<Editor>(null);

  const handleSave = () => {
    const markdown = editorRef.current?.getInstance().getMarkdown();
    console.log("Markdown Content:", markdown);
  };

  return (
    <div className="p-4">
      <Editor
        ref={editorRef}
        initialValue="Hello, Toast UI Editor!"
        previewStyle="vertical"
        height="400px"
        initialEditType="markdown"
        useCommandShortcut={true}
      />
      <button onClick={handleSave} className="mt-2 px-4 py-2 bg-blue-500 text-white rounded">
        저장하기
      </button>
    </div>
  )
}