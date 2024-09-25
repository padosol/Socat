const router = require("express").Router();

router.get("/login", (req, res)  => {
  let url = 'https://accounts.google.com/o/oauth2/v2/auth';

  url += `?client_id=${process.env.GOOGLE_CLIENT_ID}`
  url += `&redirect_uri=${process.env.GOOGLE_REDIRECT_URI}`
  url += '&response_type=code'
  url += '&scope=email profile'     

  res.redirect(url);
})

router.get("/login/oauth2/code/google", (req, res) => {
  const {code} = req.query;

  console.log(`code: ${code}`);

  res.header("Bearer: " + code)

  res.redirect("http://localhost:3000");
})


module.exports = router;  