const express = require("express");
const app = express();
const http = require("http");
const socket = require("./socket");
const cors = require('cors')
const cookieSession = require("cookie-session")
const passport = require("passport")
require("dotenv").config()

// Adding required middlewares
app.use(cookieSession({
  name: 'authSession',
  keys: ["askduhakdnkbiygvhbad7a6s*&^*S^D8asdbk"],
  maxAge: 24*60*60*100
}))

app.use(cors({
  origin: "http://localhost:3000",  //only localhost:3000 can access this server
  credentials: true  //Responding with this header to true means that the server allows cookies (or other user credentials) to be included on cross-origin requests. 
}))

const PORT = 3010;

app.use(passport.initialize())
app.use(passport.session());

//Adding Route, "/auth" is going to be perfix for all the routes which are in ./router/auth/passport
app.use('/auth', require('./Routers/auth/passport'));


const server = http.createServer(app);

socket(server);

server.listen(PORT, () => console.log(`app listening on port ${PORT}!`));