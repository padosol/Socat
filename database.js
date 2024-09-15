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
  }
})

module.exports = db;