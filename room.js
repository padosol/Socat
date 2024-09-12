const db = require('./database');

const createRoom = ({userId, roomName, roomComment}, callback) => {
  const sql = "INSERT INTO room (user_id, room_name, room_comment, created_at) VALUES (?, ?, ?, ?)";

  db.run(sql, [userId, roomName, roomComment, new Date().getTime()], (err, data) => {
    callback(err, data);
  })
}

const findAllRoom = (callback) => {
  const sql = "SELECT * FROM room WHERE delete_yn = 'N'";
  db.all(sql, [], callback)

}

const findRoomById = (roomId, callback) => {
  const sql = "SELECT * FROM room WHERE id = ?"
  db.get(sql, [roomId], (err, data) => {
    callback(err, data);
  })
} 

const softDeleteRoomById = (roomId, callback) => {

  const sql = "UPDATE room SET delete_yn = 'Y' WHERE id = ?";

  db.run(sql, [roomId], (err, data) => {
    callback(err, data);
  })

}

const updateRoomById = (comment) => {

} 


module.exports = {createRoom, findAllRoom, findRoomById, softDeleteRoomById};