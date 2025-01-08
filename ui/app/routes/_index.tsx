// export const loader: LoaderFunction = async () => {
//   return redirect('/main');
// };

export default function Index() {
  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h1 className="text-3xl font-bold mb-4">메인 페이지</h1>
      <p className="text-lg mb-4">
        환영합니다! 이 페이지는 메인 페이지입니다. 여기에서 다양한 기능과 정보를 확인할 수 있습니다.
      </p>
      <p className="text-lg">
        좌측 사이드바를 사용하여 다른 페이지로 이동하거나, 상단의 메뉴를 통해 원하는 정보를 찾아보세요.
      </p>
    </div>
  );
}
