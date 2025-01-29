import SocatList from "@/components/socat/socat-list";
import Link from "next/link";

const types: {[key: string]: string} = {
  all: "전체",
  game: "게임",
  stock: "주식",
  movie: "영화",
  news: "뉴스",
}

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
        <div className="flex justify-between items-center mb-4">
          <div className="text-lg font-semibold">
            <span>
              {types[type] || '전체'}
            </span>
          </div>
          <Link href="/socat/create" className="py-2 px-4 bg-blue-600 text-white rounded-md hover:bg-blue-700">
            소켓 생성
          </Link>
        </div>
      </div>

      <SocatList type={type}/>

    </div>
  );
}