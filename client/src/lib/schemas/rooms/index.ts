import {z} from 'zod'

const communitySchema = z.object({
  communityId: z.string(),
  communityName: z.string(),
  communityDesc: z.string(),
  topicId: z.string(),
})

export {
  communitySchema
}