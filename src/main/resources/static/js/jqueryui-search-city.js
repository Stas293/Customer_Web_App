let urlPath = '/api/v1/cities';

function addAlreadySelectedAuthotsAndKeywords() {
    $("#items-list li").each(function () {
        const author = {
            value: $(this).text().trim(),
            id: $(this).data("id")
        };
        console.log(author);
        $("#items-list li[data-id='" + author.id + "']").remove();
        addAuthorToList(author);
    });
}

window.onload = () => {
    addAlreadySelectedAuthotsAndKeywords();
    run();
}

function run() {
    $("#choices").autocomplete({
        source: function (request, response) {
            $.get(`${urlPath}`, {search: request.term.trim()}, function (data) {
                console.log(data);
                const cities = data.map(function (data) {
                    console.log(data);
                    return {
                        value: data.name,
                        id: data.id
                    };
                });

                response(cities);
            });
        },

        minLength: 2,
        select: function (event, ui) {
            const author = ui.item;
            console.log(author);
            addAuthorToList(author);
            return false;
        }
    });

    $("#items-list").on("click", ".btn-close", function () {
        $(this).parent().remove();
        let authors = $("#items-list li").map(function () {
            return $(this).data("id");
        }).get().join(",");
        $("#items-input").val(authors);
    });
}

function addAuthorToList(author) {
    if ($("#items-list li").length > 0) {
        return;
    }
    $("#choices").val("");
    $("#items-list").append("<li data-id='" + author.id + "'>" + author.value + " <button class='btn-close' aria-label='Close'></button></li>");

    const authors = $("#items-list li").map(function () {
        return $(this).data("id");
    }).get().join(",");
    $("#items-input").val(authors);
}