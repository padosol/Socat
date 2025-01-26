import {
  Room
} from './definitions';

import { rooms, rank_rooms } from './placeholder-data';

export async function fetchRooms(type: string): Promise<Room[]> {
  // const response = await fetch(`${process.env.BASE_URL}/api/rooms?type=${type}`);
  // const data = await response.json();
  return rooms;
}

export async function fetchRoomsRanking(type: string): Promise<Room[]> {
  // const response = await fetch(`${process.env.BASE_URL}/api/rooms/ranking`);
  // const data = await response.json();
  return rank_rooms;
}

export async function fetchSocatById(id: string) {
  // const response = await fetch(`${process.env.BASE_URL}/api/socats/${id}`);
  // const data = await response.json();
  return {
    id: '1',
    name: '리그 오브 레전드',
    description: '리그 오브 레전드 소켓 입니다.',
  };
}