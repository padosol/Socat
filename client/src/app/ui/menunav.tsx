import Link from "next/link";

export default function MenuNav() {
  return (
    <div className="h-12  flex items-center justify-between border-b border-gray-300 px-4">
      <div className="flex space-x-4">
        <Link href={'/'} className="text-gray-700 hover:text-gray-900">
          홈
        </Link>
        <Link href={{
          pathname: '/socat',
          query: { type: 'all' }
        }} className="text-gray-700 hover:text-gray-900">
          전체
        </Link>
        <Link href={{
          pathname: '/socat',
          query: { type: 'stock' }
        }} className="text-gray-700 hover:text-gray-900">
          주식
        </Link>
        <Link href={{
          pathname: '/socat',
          query: { type: 'game' }
        }} className="text-gray-700 hover:text-gray-900">
          게임
        </Link>
        <Link href={{
          pathname: '/socat',
          query: { type: 'movie' }
        }} className="text-gray-700 hover:text-gray-900">
          영화
        </Link>
        <Link href={{
          pathname: '/socat',
          query: { type: 'news' }
        }} className="text-gray-700 hover:text-gray-900">
          뉴스
        </Link>
      </div>
      <Link href={'/login'} className="text-gray-700 hover:text-gray-900">
        로그인
      </Link>
    </div>
  );
}