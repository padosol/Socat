const e = require("express");
const express = require("express");
const app = express();
const http = require("http");
const io = require('socket.io');


// const httpServer = http.createServer(app).listen(3333, () => {
// 	console.log("server start")
// });

// const socketServer = io(httpServer, {
// 	cors: {
// 		origin: "*",
// 		methods: ["GET", "POST"]
// 	}
// });

// socketServer.on('connect');
// socketServer.on('connect', (socket) => {

// 	socket.emit("receive_message", readItem());

// 	socket.on('send_message', (message) => {

// 		createItem(message.data, (err, data) => {
// 			if (err) {
// 				console.error(err);
// 			} else {
// 				console.log(data)
// 			}
// 		})

//     socket.broadcast.emit("receive_message", message);
//   });
// });

app.use(express.json())
app.use("/rooms", require('./routes/room'));

app.listen(3333, () => {
	console.log("server start")
})