const sqlite3 = require('sqlite3').verbose();
const charDb = 'chat.db';

const db = new sqlite3.Database(charDb, (err) => {
  if (err) {
    console.error(err.message);
  } else {
    db.run(
      `CREATE TABLE IF NOT EXISTS chat (
        id INTEGER PRIMARY KEY,
        user_id TEXT,
        room_id INTEGER,
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
