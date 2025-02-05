import { fetchRooms } from '@/lib/api/rooms/get-all-rooms';
import CurrentDate from '../currentdate';
import Link from 'next/link';

export default async function SocatList({type}: {type: string}) {

  const rooms = await fetchRooms(type);

  return (
    <div id="content-list" className="space-y-4 p-4">
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {rooms.map((room) => (
          <Link key={room.roomId} href={`/socat/${room.roomId}`}
            className='cursor-pointer hover:scale-105 transform transition-transform duration-500'
          >
            <div  className="bg-white p-4 rounded-lg shadow-md">
              <h3 className="text-lg font-semibold">
                <span>
                  {room.roomName} 
                </span>
                <span className='ml-1 text-sm text-gray-500'>
                {`(${room.roomType})`}
                </span>
              </h3>
              <p className="text-sm text-gray-500">{room.roomDesc}</p>
              <p className="text-sm text-gray-500">
                생성일: <CurrentDate date={room.createdAt}/>
              </p>
              <p className="text-sm text-gray-500"></p>
            </div>
          </Link>
        ))}
      </div>
    </div>
  )
}