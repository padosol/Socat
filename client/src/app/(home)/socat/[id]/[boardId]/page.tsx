export default function Page() {
  const relatedPosts = [
    { id: 1, title: "리그 오브 레전드 게시글 1", content: "리그 오브 레전드 소켓 입니다." },
    { id: 2, title: "리그 오브 레전드 게시글 2", content: "리그 오브 레전드 업데이트 소식입니다." },
    { id: 3, title: "리그 오브 레전드 게시글 3", content: "리그 오브 레전드 챔피언 공략입니다." },
  ];

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">리그 오브 레전드 관련 게시글</h1>
      <div className="space-y-4">
        {relatedPosts.map((post) => (
          <div key={post.id} className="bg-white p-4 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold">{post.title}</h2>
            <p className="text-gray-700">{post.content}</p>
          </div>
        ))}
      </div>
    </div>
  );
}