const sqlite3 = require('sqlite3').verbose();
const dbName = 'comment.db';

const db = new sqlite3.Database(dbName, (err) => {
  if (err) {
    console.error(err.message);
  } else {

    db.run(
      `CREATE TABLE IF NOT EXISTS room (
        id INTEGER PRIMARY KEY, 
        user_id TEXT, 
        room_name TEXT, 
        room_comment TEXT,
        delete_yn TEXT DEFAULT 'N', 
        created_at INTEGER
      )`, 
    (err) => {
      if (err) {
        console.error(err.message);
      } else {
        console.log("room table create success")
      }
    })

    db.run(
      `CREATE TABLE IF NOT EXISTS chat (
        id INTEGER PRIMARY KEY,
        user_id TEXT,
        room_id INTEGER,
        sequence INTEGER,
        comment TEXT,
        created_at INTEGER,
        delete_yn TEXT DEFAULT 'N'
      )`, 
    (err) => {
      if (err) {
        console.error(err);
      } else {
        console.log("chat table create success")
      }
    })

  }
})

module.exports = db;