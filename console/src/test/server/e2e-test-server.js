var express = require('express');
var params = require('express-params');
var http = require('http');
var fs = require('fs');


var app = express();
var server = http.createServer(app);
params.extend(app);

app.use(express.json());
app.use(express.cookieParser());

app.use(function (request, response, next) {
    console.log(request.url);
    response.set({
        'Cache-Control': 'no-cache, must-revalidate'
    });
    if (request.get('Origin')) {
        response.set({
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, DELETE, PUT',
            'Access-Control-Allow-Headers': 'accept, origin, content-type',
            'Access-Control-Max-Age': 3600
        });
    }
    if (request.method == 'OPTIONS') {
        response.send(200);
    } else {
        next();
    }
});

eval(fs.readFileSync('mock-tenants.js') + '');
eval(fs.readFileSync('mock-workspaces.js') + '');
eval(fs.readFileSync('mock-agent-certificate.js') + '');
eval(fs.readFileSync('mock-virtual-locations.js') + '');
eval(fs.readFileSync('mock-projects.js') + '');
eval(fs.readFileSync('mock-lookuptables.js') + '');
eval(fs.readFileSync('mock-lookuptables-entries.js') + '');
eval(fs.readFileSync('mock-deployments.js') + '');
eval(fs.readFileSync('mock-adapters.js') + '');

eval(fs.readFileSync('mock-virtual-locations-add-agent.js') + '');
eval(fs.readFileSync('mock-agents.js') + '');
eval(fs.readFileSync('mock-central-locations.js') + '');
eval(fs.readFileSync('mock-tenant-central-locations.js') + '');
eval(fs.readFileSync('mock-credentials.js') + '');

eval(fs.readFileSync('mock-monitor-tenants.js') + '');
eval(fs.readFileSync('mock-monitor-deployments.js') + '');
eval(fs.readFileSync('mock-monitor-locations.js') + '');

server.listen(9080);
console.log('listening on port 9080');
