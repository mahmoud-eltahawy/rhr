"use strict";
(function () {
    const allButtons = document.getElementsByTagName("button");
    for (let i = 0; i < allButtons.length; i++) {
        const btn = allButtons[i];
        btn.addEventListener("click", function () {
            const btnId = btn.getAttribute("id");
            if (btnId) {
                const actionPlace = document.getElementById("action-place");
                const targetForm = document.getElementById(btnId.slice(0, -4));
                if (targetForm && actionPlace) {
                    actionPlace.innerHTML = targetForm.innerHTML;
                }
                else {
                    throw new Error("target form or action place is null ");
                }
            }
            else {
                throw new Error("button id is null ");
            }
        });
    }
})();
