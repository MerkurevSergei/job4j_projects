requirejs.config({
    'baseUrl': 'resources/js',
    'paths': {
        'domReady': 'lib/domReady',
        'dateformat': 'lib/jquery-dateformat.min',
        'jsRender': 'lib/jsrender.min',
        'cards-manager': 'modules/cards-manager',
        'card': 'modules/card',
        'task-filter': 'modules/task-filter',
        'task-builder': 'modules/task-builder',
        'security-form': 'modules/security-form',
    }
});

// Load the main app module to start the app
require(['domReady!', 'cards-manager', 'card', 'dateformat', 'security-form', 'task-builder', 'task-filter', 'jsRender'],
    function (document, cardsmanager) {
        cardsmanager.updateItems(false);
    }
);