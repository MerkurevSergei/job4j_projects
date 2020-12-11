define('card', ['cards-manager'], function (cardsmanager) {
    $('.card-container').on('change', '.card-check-complete', (function(e) {
        cardsmanager.changeState(e.target.value, e.target.checked);
    }));
});
