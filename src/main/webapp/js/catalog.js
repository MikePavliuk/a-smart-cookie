$(document).ready(function(){
    let input = document.querySelector("#search-text");
    let button = document.querySelector("#search-button");
    button.disabled = true;
    input.addEventListener("change", stateHandle);

    function stateHandle() {
        button.disabled = document.querySelector("#search-text").value === "";
    }

});