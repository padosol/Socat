import {
  useRouteLoaderData
} from "react-router-dom"

import { IUserInfo } from "../../../store/userInfo";
import { getMyRooms } from "../../../api/room";

export function loader() {

  // const response = await getMyRooms();

}

const MyPage = () => {
  const {userInfo} = useRouteLoaderData("main") as {userInfo: IUserInfo};
  console.log(userInfo)

  return ( 
    <div>
      <div>
        <div>
          <span>이름: </span>
          <span>{userInfo.username}</span>
        </div>
        <div>
          <span>이메일:</span>
          <span>{userInfo.email}</span>
        </div>
      </div>
      <div>
        내가 생성한 방
      </div>
    </div>
   );
}
 
export default MyPage;