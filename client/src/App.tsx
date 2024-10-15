import './App.css'
import { useEffect, useState } from 'react'
import axios from 'axios';

import * as StompJs from "@stomp/stompjs";

interface Room {
	roomId: string,
	roomName: string
}

interface Message {
	roomId: string,
	writer: string,
	message: string
}

function App() {

	const [inputValue, setInputValue] = useState("");
	const [rooms, setRooms] = useState<Room[]>([]);
	const [client, setClient] = useState<StompJs.Client>();
	const [room, setRoom] = useState<Room>(null);
	const [chatMessage, setChatMessage] = useState<string>("");
	const [messages, setMessages] = useState<Message[]>([]);

	useEffect(() => {
		fetchRooms();
	}, [])

	const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
		setInputValue(e.target.value)
	}

	const handleCreateButtonClick = async () => {
		const response = await axios.post("/api/rooms", {roomName: inputValue});

		if(response.status === 201) {
			fetchRooms();
		}
	}

	const fetchRooms = async () => {
		const response = await axios.get('/api/rooms');
		setRooms([...response.data])
	}


  const connect = (room: Room) => {
    // 소켓 연결
    try {
      const clientdata = new StompJs.Client({
        brokerURL: "ws://localhost:8888/ws/stomp",
        connectHeaders: {
          token: "",
        },
        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000, // 자동 재 연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

			clientdata.onConnect = () => {

				setClient(clientdata)
				setRoom(room)

				clientdata.publish({
					destination: "/pub/chat/enter",
					body: JSON.stringify({
						roomId: room.roomId,
						writer: "tester",
						message: "입장"
					}),
				})

				clientdata.subscribe(`/sub/chat/room/${room.roomId}`, (message) => {
					const resMessage: Message = JSON.parse(message.body)

					setMessages((prevMessages) => [...prevMessages, resMessage])
				})
			}

			clientdata.activate(); 


    } catch (err) {
      console.log(err);
    }
  };


	const sendMessage = (room: Room) => {

		client?.publish({
			destination: "/pub/chat/message",
      body: JSON.stringify({
				roomId: room.roomId,
				writer: "tester",
				message: chatMessage
      }),
		})
	}

	const handleChatMessageInput = (e:React.ChangeEvent<HTMLInputElement>) => {
		setChatMessage(e.target.value)
	}

	return (
		<>
			<div className='w-full h-full flex flex-col justify-center pt-5'>
				<div className='flex justify-center'>
					<input className='border border-black mr-2 rounded-lg px-2' value={inputValue} onChange={handleInput}/>
					<div className='border border-black px-2 rounded-md hover:bg-gray-300 cursor-pointer'
						onClick={handleCreateButtonClick}				
					>
						방만들기
					</div>
				</div>
				<div className='p-5'>
					<div className='border'>
						<div>방리스트</div>

						{
							rooms.map( (room, index) => (
								<div key={index} className='cursor-pointer hover:bg-slate-300'
									onClick={() => connect(room)}
								>
									{room.roomName}
								</div>
							))
						}
					</div>
				</div>

				<div className='p-5'>

					<div className='border'>

						<div>
							<span>채팅방</span>
							<span> 제목: { room ? room.roomName: ''}</span>
						</div>

						<div>
							{
								messages.map((message, index) => (
									<div key={index}>
										{message.message}
									</div>
								))
							}
						</div>

						<input className='border border-black' value={chatMessage} onChange={handleChatMessageInput}/>
						<button onClick={() => sendMessage(room)}>보내기</button>

					</div>

				</div>

			</div>
		</>
	)
}

export default App
