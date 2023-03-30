'use strict';
const {
    Model
} = require('sequelize');

module.exports = (sequelize, DataTypes) => {
    class Group extends Model {
        static associate(models) {
            Group.hasMany(models.Message, {
                as: 'messages',
                foreignKey: 'toGroupId'
            });
        }
    }

    Group.init({
        name: {
            type: DataTypes.STRING,
            allowNull: false
        },
        location: {
            type: DataTypes.STRING,
            allowNull: false
        }
    }, {
        sequelize,
        modelName: 'Group',
        tableName: 'group'
    });

    return Group;
};