import { Viewer } from "@toast-ui/react-editor";

export default function EditorViewer({
  initialValue
}: {
  initialValue: string;
}) {
  return (
    <Viewer 
      initialValue={initialValue} 
      height="500px"
    />
  );
}