const express = require('express');
const app = express();
const cors = require('cors');

require('dotenv').config();
global.cfg = require('./config/config.js');

const port = process.env.PORT || 3000;
const ip = '127.0.0.1';

const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false, limit: '50mb' }));
app.use(bodyParser.json({ limit: '50mb' }));

app.use(cors({origin: true, credentials: true}));
app.use('/assets', express.static('assets'));

const database = require('./core/database.js');
const routes = require('./config/routes.js');
const Router = require('./core/router.js');
const router = new Router(app, routes, database);
router.setup();

app.listen(port, ip, () => {
    console.log(`server is listening at ${ip}:${port}`);
});