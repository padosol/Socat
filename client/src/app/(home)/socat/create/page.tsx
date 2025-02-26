import Form from "@/components/socat/create-form";
import { getAllTopic } from "@/lib/api/communities/topic/get-all-topics";

export default async function Page() {

  const topics = await getAllTopic();

  return (
    <Form topics={topics} />
  );
}