const router = require("express").Router();
const {createRoom, findAllRoom, findRoomById, softDeleteRoomById} = require('../room');

router.post("", (req, res) => {
	const {roomName, userId, roomComment} = req.body;

	createRoom({roomName, userId, roomComment}, (err, data) => {
		if(err) {
			res.status(500).send(err.message);
		} else {
			res.status(201).send(data);
		}
	})
})

router.get('/:id', (req, res) => {
		const roomId = req.params.id;

		findRoomById(roomId, (err, data) => {
			if(err) {
				res.status(500).send(err.message);
			} else {
				res.status(200).send(data);
			}
		})
})

router.get('', (req, res) => {
	findAllRoom((err, data) => {
		res.status(200).send(data);
	})
})

router.delete('/:id', (req, res) => {
	const roomdId = req.params.id;

	softDeleteRoomById(roomdId, (err, data) => {
		if(err) {
			res.status(500).send(err.message);
		} else {
			res.status(204).send();
		}
	})
})

module.exports = router;