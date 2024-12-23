import {
  LoaderFunctionArgs
} from "react-router-dom"

export async function loader() {
  console.log(localStorage.getItem("user-info"))
  

  return {}
}

const MyPage = () => {
  return ( 
    <div>
      MyPage
    </div>
   );
}
 
export default MyPage;