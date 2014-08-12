(function () {
    app.get('/tenants/', function (request, response) {
        response.json(200, {
            "content": [
                {
                    "identifier": "f70cc0d8-24ec-4a11-8b5a-0fcaa6066b98",
                    "name": "gg",
                    "description": "d",
                    "organizationName": "o",
                    "contactInformation": "c"
                },
                {
                    "identifier": "dbdaa93e-9d1d-4110-8a43-63345a0c6e24",
                    "name": "tenant1",
                    "description": "d2",
                    "organizationName": "o2",
                    "contactInformation": "c2"
                },
                {
                    "identifier": "c46fa018-8791-4880-afd9-f6197ee95543",
                    "name": "tenant2",
                    "description": "dddddddddddddddddd",
                    "organizationName": "o2",
                    "contactInformation": "c2"
                }
            ]
        });
    });


    app.post('/tenants/', function (request, response) {
        response.send(200);
    });

    app.put('/tenants/f70cc0d8-24ec-4a11-8b5a-0fcaa6066b98', function (request, response) {
        response.send(200);
    });

    app.get('/agents/f70cc0d8-24ec-4a11-8b5a-0fcaa6066999/shared-tenants/',function(request,response){
        response.send(200);
    }) ;

    app.get('/agents/f70cc0d8-24ec-4a11-8b5a-0fcaa6067000/shared-tenants/',function(request,response){
        response.send(200);
    }) ;

    app.delete('/tenants/f70cc0d8-24ec-4a11-8b5a-0fcaa6066b98', function (request, response) {
        response.send(200);
    });

    app.get("/tenants/f70cc0d8-24ec-4a11-8b5a-0fcaa6066b98", function (request, response) {
        response.json(200, {
            "identifier": "f70cc0d8-24ec-4a11-8b5a-0fcaa6066b98",
            "name": "gg",
            "description": "d",
            "organizationName": "o",
            "contactInformation": "c"
        });
    });
})();