const db = require('./database');

const createChat = ({userId, roomId, comment}, callback) => {
  const sql = "INSERT INTO chat (user_id, room_id, comment, created_at) VALUES(?,?,?,?)"

  db.run(sql, [userId, roomId, comment, new Date().getTime()], (err, data) => {
    callback(err, data);
  })

}

const findAllChatByRoomId = (roomId, callback) => {
  const sql = "SELECT * FROM chat WHERE room_id = ? AND delete_yn = 'N' ORDER BY created_at desc";

  db.all(sql, [roomId], (err, data) => {
    callback(err, data);
  })

}

module.exports = {createChat, findAllChatByRoomId}

