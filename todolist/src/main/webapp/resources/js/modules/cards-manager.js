define('cards-manager', function () {

    /* *************************************************************************** */

    /* ADD ITEM AJAX ************************************************************* */
    function addItem(formJson) {
        $.ajax({
            url: '/todolist/item',
            method: 'post',
            dataType: 'json',
            data: formJson,
            success: function (data) {
                if (!data.id) return;
                $('.card-container').append(
                    $.templates("#cardTemplate").render({
                            id: data.id,
                            title: data.title,
                            description: data.description,
                            created: $.format.prettyDate(new Date(data.created)),
                            checked: (data.done) ? "checked" : "",
                            categories: data.categories
                        },
                        {
                            getCategoryClass: function (i) {
                                let classes = ["bg-primary", "bg-success", "bg-warning"];
                                return classes[i % 3];
                            }
                        })
                );
            }
        });
    }

    /* ***************************************************************************** */

    /* UPDATE ITEMS AJAX *********************************************************** */
    function updateItems(withCompleted) {
        $.when(
            $.getJSON('http://localhost:8080/todolist/item?withCompleted=' + withCompleted)
        ).then(function (data) {
            $('.card-container').empty();
            $.each(data, function (index, value) {
                $('.card-container').append(
                    $.templates("#cardTemplate").render({
                            id: value.id,
                            title: value.title,
                            description: value.description,
                            created: $.format.prettyDate(new Date(value.created)),
                            checked: (value.done) ? "checked" : "",
                            categories: value.categories
                        },
                        {
                            getCategoryClass: function (i) {
                                let classes = ["bg-primary", "bg-success", "bg-warning"];
                                return classes[i % 3];
                            }
                        })
                );
            });
        }, function (err) {
            console.log(err);
        });
    }

    /* ********************************************************************* */

    /* CHANGE STATE ITEM AJAX ********************************************** */
    function changeState(id, checked) {
        $.ajax({
            url: '/todolist/item',
            method: 'post',
            dataType: 'json',
            data: {method: 'update', id: id, done: checked},
        }).then(function () {
            updateItems($('#task-filter__switcher-checkbox').prop('checked'));
        }).fail(function (err) {
            console.log(err);
        });
    }

    return {
        addItem: addItem,
        updateItems: updateItems,
        changeState: changeState
    };
});
