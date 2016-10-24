//$undertow.setDefault('template_type', 'mustache');

$undertow
    .onGet('/api/javascript',
        {headers: {'content-type': 'text/plain'}},
        [function ($exchange) {
            return 'Hello World ' + ($exchange.param('name') || '') + ' from JavaScript';
        }])

    .onGet('/api/javascript/json',
        {headers: {'content-type': 'application/json'}},
        [function ($exchange) {
            return {value1: 'Hello World', value2: 'from JavaScript'};
        }])

    .onGet('/api/javascript/tpl',
        {template: 'sample-template.hbs'},
        [function ($exchange) {
            return {message: 'World'};
        }])
;