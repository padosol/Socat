import { fetchRooms } from '@/lib/data';
import Link from 'next/link';

export default async function SocatList() {

  const rooms = await fetchRooms('all');
  return (
    <div id="content-list" className="space-y-4 p-4">
      <div>
        소켓방 리스트
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {rooms.map((room) => (
          <div key={room.id} className="bg-white p-4 rounded-lg shadow-md">
            <h3 className="text-lg font-semibold">
              <span>
                {room.title} 
              </span>
              <span className='ml-1 text-sm text-gray-500'>
              {`(${room.owner})`}
              </span>
              {/* <Link href={`/socat/${room.id}`} className="text-blue-600 hover:underline">
              </Link> */}
            </h3>
            <p className="text-sm text-gray-500">{room.description}</p>
            <p className="text-sm text-gray-500">생성일: {room.createdAt}</p>
            <p className="text-sm text-gray-500"></p>
          </div>
        ))}
      </div>
    </div>
  )
}