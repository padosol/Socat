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