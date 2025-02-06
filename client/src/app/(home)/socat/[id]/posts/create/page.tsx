import Form from "@/components/socat/[id]/posts/create/create-form";

export default async function Page(props: {
  params: Promise<{ 
    id: string,
  }>,
}) {

  const params = await props.params;
  const roomId = params.id;


  // props 으로 roomId 전송
  // token 에서 userId 빼서 사용

  return (
    <div>
      <Form roomId={roomId}/>
    </div>
  )
}