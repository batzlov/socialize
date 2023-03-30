const AuthenticationController = require('../controllers/authenticationController');
const UsersController = require('../controllers/usersController');
const UtilController = require('../controllers/utilController');

let routes = {
    'authentication': {
        controller: AuthenticationController,
        actions: [
            { path: '/sign-up', action: 'signUp', method: 'post' },
            { path: '/sign-in', action: 'signIn', method: 'post' },
            { path: '/sign-out', action: 'signOut', method: 'get' },
        ]
    },
    'users': {
        controller: UsersController,
        actions: [
            { path: '/users', action: 'getUsers', method: 'get' },
            { path: '/users/:id', action: 'getUserById', method: 'get' },
        ]
    },
    'util': {
        controller: UtilController,
        actions: [
            { path: '/', action: 'index', method: 'get' },
            { path: '**', action: '404', method: 'get' },
        ]
    },
};

module.exports = routes;