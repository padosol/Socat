import { Room } from "@/app/lib/definitions"

export default function Card({room}: {room: Room}) {
  return (
    <div key={room.id} className="bg-white p-4 rounded-lg shadow-md min-w-[200px]">
    <h3 className="text-lg font-semibold">{room.title}</h3>
    <p className="text-sm text-gray-500">작성일자: {room.createdAt}</p>
    <p className="text-sm text-gray-500">작성자: {room.owner}</p>
  </div>
  )
}