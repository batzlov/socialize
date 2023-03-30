'use strict';
module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.createTable('message', {
            id: {
                allowNull: false,
                autoIncrement: true,
                primaryKey: true,
                type: Sequelize.INTEGER
            },
            text: {
                type: Sequelize.STRING
            },
            fromId: {
                type: Sequelize.INTEGER,
                references: {
                    model: {
                        tableName: 'user',
                    },
                    key: 'id'
                },
            },
            toId: {
                type: Sequelize.INTEGER,
                references: {
                    model: {
                        tableName: 'user',
                    },
                    key: 'id'
                },
                allowNull: true
            },
            isGroupChatMessage: {
                type: Sequelize.BOOLEAN,
                allowNull: false
            },
            toGroupId: {
                type: Sequelize.INTEGER,
                references: {
                    model: {
                        tableName: 'group',
                    },
                    key: 'id'
                },
                allowNull: true
            },
            readBy: {
                allowNull: false,
                type: Sequelize.STRING,
                get() {
                    var readBy = this.getDataValue('readBy') !== null ? this.getDataValue('readBy').split(',') : null;
                    return readBy;
                },
                set(val) {
                    this.setDataValue('readBy', val.join(','));
                },
            },
            createdAt: {
                allowNull: false,
                type: Sequelize.DATE
            },
            updatedAt: {
                allowNull: false,
                type: Sequelize.DATE
            }
        });
    },
    async down(queryInterface, Sequelize) {
        await queryInterface.dropTable('message');
    }
};