import React, { useState } from 'react';
import { useLoaderData } from '@remix-run/react';
import { LoaderFunction } from '@remix-run/node';

export const loader: LoaderFunction = async ({ params }) => {
  const { type } = params;
  return { type };
};

const Content = () => {
  const { type } = useLoaderData<{ type: string }>();
  const [rooms, setRooms] = useState<string[]>([]);

  const handleCreateRoom = () => {
    const newRoomName = `Room ${rooms.length + 1}`;
    setRooms([...rooms, newRoomName]);
  };

  return (
    <section className="flex flex-col  p-4">
      <div className="flex justify-between items-center mb-4 bg-gray-100 p-4">
        <h2 className="text-xl font-semibold">{type}</h2>
        <button
          className="bg-green-700 text-white px-4 py-2 rounded"
          onClick={handleCreateRoom}
        >
          Create Room
        </button>
      </div>
      <div>
        <h3 className="text-lg font-semibold">Created Rooms:</h3>
        <ul className="list-disc list-inside">
          {rooms.map((room, index) => (
            <li key={index} className="mt-2">{room}</li>
          ))}
        </ul>
      </div>
    </section>
  );
};

export default Content;