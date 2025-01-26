import { z } from 'zod'


const loginFormSchema = z.object({
  email: z.string().email({message: "이메일을 입력해주세요."}),
  password: z.string().min(4).max(8),
  username: z.string().min(2),
})

export {
  loginFormSchema
}