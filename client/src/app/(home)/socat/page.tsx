import SocatList from "@/components/socat/socat-list";
import Link from "next/link";
import { AuthButton } from "@/components/button";

export default async function Page(props: {
  searchParams?: Promise<{
    type?: string;
  }>;
}) {
  const searchParams = await props.searchParams;
  const type = searchParams?.type || '';

  return (
    <div>
      <div className="bg-slate-200 p-2"> 
        <div className="flex justify-between items-center">
          <div className="text-lg font-semibold">
            <span>
            </span>
          </div>
          <AuthButton className="">
            <Link href="/socat/create">
              커뮤니티 생성
            </Link>
          </AuthButton>
        </div>
      </div>

      <SocatList type={type}/>

    </div>
  );
}