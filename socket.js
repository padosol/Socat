const SocketIO = require("socket.io");

module.exports = (server) => {
  const io = SocketIO(server, {
    cors: {
      origin: "http://localhost:3000",
      methdos: ["GET", "POST"]
    }
  });
  const chat = io.of("/chat")

  chat.on("connection", (socket) => {

    socket.on("welcome", (data) => {

      const name = socket.name = data.name;
      const room = socket.room = data.roomId;

      socket.join(room);

      // 본인에게 
      socket.emit("welcomeRoom", room);

      // room 맴버들에게
      socket.to(room).emit("newUserEnter", name)
    })

    socket.on("disconnect", () => {
      console.log("client disconnected")
    })


    socket.on("chat", (data) => {
      console.log(data)

      const room = data.roomId;

      chat.in(room).emit('chat', data.text);
    })

  })


}