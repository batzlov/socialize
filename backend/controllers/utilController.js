const path = require('path');
const fs = require('fs');
const Controller = require('./mainController.js');
const Passport = require('../core/passport.js');
const fetch = require('node-fetch');

class UtilController extends Controller
{
    constructor(...args)
    {
        super(...args);

        const self = this;

        self.format = Controller.HTTP_FORMAT_JSON;
    }

    actionIndex() 
    {
        const self = this;

        self.res.sendFile(path.join(__dirname, '..', 'index.html'));
    }

    action404() 
    {
        const self = this;

        self.render({
            message: 'Die angefragte Schnittstelle/Ressource existiert nicht!'
        });
    }
}

module.exports = UtilController;