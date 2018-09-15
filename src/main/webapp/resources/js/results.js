function search(phrase) {
    var safePhrase = phrase.replace("#", "!-!");
    if (phrase !== '') {
        jQuery.ajax({
            type: "GET",
            url: "search?phrase=" + safePhrase,
            data: phrase,
            dataType: "text",
            success: function (data, status, jqXHR) {
                refreshResults(data);
            },
            error: function (jqXHR, status) {
                jQuery("#resultsBox").text("Error Occured. status : " + status);
            }
        });
    } else {
        jQuery("#resultsBox").text("");
    }
}
function refreshResults(data) {
    var jsonData = jQuery.parseJSON(data);
    var resultsBox = jQuery("#resultsBox");
    resultsBox.text('');
    jQuery.each(jsonData, function (idx, programmingLanguage) {
        resultsBox.html(resultsBox.html()
                + "<div class='singleResultDiv'>"
                + "<span class='singleResultName'>" + programmingLanguage.Name + "</span><br />"
                + "<span class='singleResultLabel'>Type : </span>"
                + "<span class='singleResultValue'>" + programmingLanguage.Type + "</span><br />"
                + "<span class='singleResultLabel'>Designed by : </span>"
                + "<span class='singleResultValue'>" + programmingLanguage["Designed by"] + "</span><br />"
                + "<span class='gray'>Relevancy level : </span>"
                + "<span class='gray'>" + programmingLanguage.relevancyLevel + "</span><br />"
                + "</div>"
                );
    });
}