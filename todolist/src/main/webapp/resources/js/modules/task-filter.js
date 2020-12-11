define('task-filter', ['cards-manager'], function (cardsmanager) {
    $('#task-filter__switcher-checkbox').change(function() {
        if(this.checked) {
            cardsmanager.updateItems(true);
        } else {
            cardsmanager.updateItems(false);
        }
    });
});
