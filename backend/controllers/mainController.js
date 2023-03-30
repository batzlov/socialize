const Controller = require('../core/controller.js');

class MainController extends Controller
{
    constructor(...args)
    {
        super(...args);

        const self = this;

        // set default unauthorized
        self.req.authorized = false;
        self.req.user = null;
    }
}

module.exports = MainController;