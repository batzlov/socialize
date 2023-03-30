'use strict';
const {
    Model
} = require('sequelize');

module.exports = (sequelize, DataTypes) => {
    class Message extends Model {
        static associate(models) {
            Message.belongsTo(models.User, {
                as: 'from',
                foreignKey: 'fromId'
            });
    
            Message.belongsTo(models.User, {
                as: 'to',
                foreignKey: 'toId'
            });

            Message.belongsTo(models.Group, {
                as: 'toGroup',
                foreignKey: 'toGroupId'
            });
        }
    }

    Message.init({
        text: DataTypes.STRING,
        fromId: DataTypes.INTEGER,
        toId: DataTypes.INTEGER,
        isGroupChatMessage: DataTypes.BOOLEAN,
        toGroupId: DataTypes.INTEGER
    }, {
        sequelize,
        modelName: 'Message',
        tableName: 'message'
    });

    return Message;
};