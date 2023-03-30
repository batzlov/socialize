const Controller = require('../core/controller.js');

class UsersController extends Controller
{
    constructor(...args)
    {
        super(...args);

        const self = this;

        self.format = Controller.HTTP_FORMAT_JSON;
    }

    async actionIndex() 
    {
        const self = this;

        self.render({
            message: 'Hello World!'
        });
    }

    async actionGetUsers() 
    {
        const self = this;

        let users = [];
        let error = null;

        try {
            users = await self.db.User.findAll({
                where: {}
            });
        } catch(err) {
            error = err;
        }

        if(error !== null) 
        {
            self.render({
                details: error
            }, {
                statusCode: 400
            });
        }
        else 
        {
            self.render(users);
        }
    }

    async actionGetUserById() 
    {
        const self = this;
        const id = self.param('id');

        let user = null;
        let error = null;

        try {
            user = await self.db.User.find({
                where: {
                    id: id
                }
            });

            console.log(user)

            if(!user) {
                error = new Error(`User with the given id: ${id} was not found`);
            }
        } catch(err) {
            error = err;
        }

        if(error !== null) 
        {
            self.render({
                details: error
            }, {
                statusCode: 400
            });
        }
        else 
        {
            self.render(user);
        }
    }
}

module.exports = UsersController;