'use strict';
const {
    Model
} = require('sequelize');

module.exports = (sequelize, DataTypes) => {
    class User extends Model {
        static associate(models) {}
    }
    
    User.init({
        firstName: {
            type: DataTypes.STRING,
            allowNull: false
        },
        lastName: {
            type: DataTypes.STRING,
            allowNull: false
        },
        email: {
            type: DataTypes.STRING,
            allowNull: false
        },
        password: {
            type: DataTypes.STRING,
            allowNull: false
        },
        isAdmin: {
            type: DataTypes.BOOLEAN,
            allowNull: false
        }
    }, {
        sequelize,
        modelName: 'User',
        tableName: 'user',
    });

    return User;
};