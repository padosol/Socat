const router = require("express").Router();
const {createChat, findAllChatByRoomId} = require('../chat');

// 채팅 추가
router.post("", (req, res) => {
  const {userId, roomId, comment} = req.body;

  createChat({userId, roomId, comment}, (err, data) => {
    if (err) {
      res.status(500).send(err.message);
    } else {
      res.status(200).send(data);
    }
  })
})

router.get("/:roomId", (req, res) => {
  const roomdId = req.params.roomId;

  findAllChatByRoomId(roomdId, (err, data) => {
    if (err) {
      res.status(500).send(err.message);
    } else {
      res.status(200).send(data);
    }
  });
})

module.exports = router;