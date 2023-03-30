require('dotenv').config();

module.exports = {
    development: {
        username: process.env.DB_USER || 'root',
        password: process.env.DB_PASSWORD ||'root',
        database: process.env.DB_NAME ||'socialize',
        dialect: 'mysql',
        host: process.env.DB_HOST || 'localhost',
        port: process.env.DB_PORT || '8889'
    },
    test: {
        // TODO: configure test database
    },
    production: {
        // TODO: configure production
    },
};