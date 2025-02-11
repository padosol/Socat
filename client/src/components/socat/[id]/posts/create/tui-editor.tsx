import dynamic from "next/dynamic";
import "@toast-ui/editor/dist/toastui-editor.css";

const WrappedEditor = dynamic(() => import("./editor-wrapper"), {
  ssr: false,
});

export default function TuiEditor(props) {
  return <WrappedEditor {...props}/>;
};