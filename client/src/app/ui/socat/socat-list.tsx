import { fetchRooms, fetchRoomsRanking } from '@/app/lib/data';
import Card from './card';
import Link from 'next/link';

export default async function SocatList() {

  const rooms = await fetchRooms('all');
  const rank_rooms = await fetchRoomsRanking();

  return (
    <div id="content-list" className="space-y-4 p-4">
      {/* Card List */}
      <div className='text-lg font-semibold'>
        인기 있는 소켓방
      </div>
      <div className="relative">
        <div className="flex overflow-x-auto space-x-4 p-2">
          {rank_rooms.map((room) => (
            <Card key={room.id} room={room} />
          ))}
        </div>
      </div>

      {/* Table List */}

      <div>
        소켓방 리스트
      </div>
      <div className="bg-white p-4 rounded-lg shadow-md overflow-x-auto">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">no</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">제목</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">작성일자</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">작성자</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {rooms.map((room) => (
              <tr key={room.id}>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 cursor-pointer">
                  <Link href={`/socat/${room.id}`} className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {room.title}
                  </Link>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.createdAt}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.owner}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}