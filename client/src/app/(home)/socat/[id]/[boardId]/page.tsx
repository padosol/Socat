import getRoomById from "@/lib/api/rooms/get-room-id";
import { notFound } from "next/navigation";

export default async function Page(props: {
  params?: Promise<{
    boardId?: string;
  }>
}) {

  const params = await props.params;
  const boardId = params?.boardId || "";

  const room = await getRoomById(boardId);

  if (!room) {
    notFound();
  }


  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">리그 오브 레전드 관련 게시글</h1>
      <div className="space-y-4">

      </div>
    </div>
  );
}