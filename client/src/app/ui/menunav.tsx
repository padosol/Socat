import Link from "next/link";

export default function MenuNav() {
  return (
    <div className="h-12 bg-gray-50 flex items-center justify-between border-b border-gray-300 px-4">
      <div className="flex space-x-4">
        <a href="#" className="text-gray-700 hover:text-gray-900">전체</a>
        <a href="#" className="text-gray-700 hover:text-gray-900">주식</a>
        <a href="#" className="text-gray-700 hover:text-gray-900">게임</a>
        <a href="#" className="text-gray-700 hover:text-gray-900">영화</a>
        <a href="#" className="text-gray-700 hover:text-gray-900">정치</a>
      </div>
      <Link href={'/login'} className="text-gray-700 hover:text-gray-900">
        로그인
      </Link>
    </div>
  );
}