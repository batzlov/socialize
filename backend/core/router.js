class Router
{
    constructor(app, routes, database)
    {
        const self = this;

        self.app = app;
        self.routes = routes;
        self.hashMap = [];
        self.database = database;
    }
 
    setup()
    {
        const self = this;
        
        // go though all routes
        for(let name in self.routes)
        {
            const group = self.routes[name];
            for(let index = 0; index < group.actions.length; ++index)
            {
                const action = group.actions[index];
                self.app[action.method.toLowerCase()](action.path, (req, res) => {
                    let controller = new group.controller(self, req, res, action.action);
                    let actionName = 'action' + action.action.charAt(0).toUpperCase() + action.action.slice(1);
                    
                    // controller[actionName]();

                    controller.executeBeforeList(() => {
                        if(typeof controller[actionName] == 'function') 
                        {
                            controller[actionName]();
                        }
                    });
                });

                // hash
                let hash = name + action.action + action.method.toLowerCase();

                const str = action.path;
                let m;
                let urlParams = [];
                const regexParams =  /:([a-zA-Z](?:[a-zA-Z0-9]+))?/g;

                while((m = regexParams.exec(str)) !== null)
                {
                    if(m.index === regexParams.lastIndex)
                    {
                        ++regexParams.lastIndex;
                    }

                    urlParams.push(m[1]);
                }

                urlParams.sort();

                if(urlParams.length > 0)
                {
                    hash += urlParams.join('');
                }

                self.hashMap[hash] = action.path;
            }
        }
    }

    urlFor(controller, action, params = null, method = 'GET')
    {
        const self = this;

        let hash = controller + action + method.toLowerCase();

        let paramsKeys = null;
        if(params !== null)
        {
            paramsKeys = Object.keys(params).sort();
            hash += paramsKeys.join('');
        }

        let path = '/';
        if(self.hashMap[hash])
        {
            path = self.hashMap[hash];
            if(paramsKeys !== null)
            {
                for(let index = 0; index < paramsKeys.length; ++index)
                {
                    path = path.replace(':' + paramsKeys[index], params[paramsKeys[index]]);
                }
            }
        }
        else
        {
            console.error(`Can not generate any url for controller name: ${controller} action: ${action} method: ${method} and params:`, params);
        }

        return path;
    }
}

module.exports = Router;