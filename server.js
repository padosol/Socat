const express = require("express");
const app = express();
const http = require("http");
const socket = require("./socket");
const server = http.createServer(app);

const PORT = 3010;

socket(server);

server.listen(PORT, () => console.log(`app listening on port ${PORT}!`));