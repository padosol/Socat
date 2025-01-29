import {z} from 'zod'

const roomSchema = z.object({
  roomId: z.string(),
  roomName: z.string(),
  roomDesc: z.string(),
  roomType: z.string(),
})

export {
  roomSchema
}