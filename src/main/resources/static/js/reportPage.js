"use strict";
const jsonMap = new Map(Object
    .entries(JSON.parse(document.getElementById("catsContainer").innerText)));
function addMessage() {
    document.getElementById("messager")
        .innerText = new URL(window.location.href)
        .searchParams.get("message");
}
function replaceForm(machine, number, fieldId) {
    document.getElementById(fieldId).innerHTML = `
		<div class="box-container">
			<div class="form-container">
				<h1>${number == 0 ? machine : machine + ' ' + number} PROBLEM</h1>
				<form action="/report/problem" method="post">
				<input type="hidden" id="category" name="category" value="${machine}">
				<input type="hidden" id="number" name="number" value="${number}">
				<label name="problems" id="problems">which problem</label>
				<select multiple="multiple" name="problems" id="problems" required>
          ${(function () {
        let options = "";
        const problems = JSON.parse(document.getElementById("problemsContainer").innerText);
        for (let p of problems) {
            options += `<option value="${p}" >${p}</option>`;
        }
        return options;
    })()}
				</select>
				<label name="beginTime" id="beginTime">Problem begin</label>
				<input type="time" id="beginTime" name="beginTime" required>
				<label name="endTime" id="endTime">Problem end</label>
				<input type="time" id="endTime" name="endTime"   required>
				<button type="submit">Submit</button>
				</form>
			</div>
		</div>
`;
}
function replaceButtons(catName, fieldId) {
    let arr = jsonMap.get(catName);
    if (arr != null) {
        if (arr[0]) {
            replaceForm(catName, 0, fieldId);
        }
        else {
            document.getElementById(fieldId).innerHTML = (function () {
                let buttons = "";
                for (let n of arr) {
                    buttons += `<button class="cat-button"
                        onclick="replaceForm('${catName}','${n}','${fieldId}')">
                        ${catName} ${n}
                      </button>`;
                }
                return buttons;
            })();
        }
    }
}
function addButtons() {
    let btnString = "";
    for (let catName of jsonMap.keys()) {
        btnString += `<button class="cat-button"
                              onclick="replaceButtons('${catName}','add-problem-field')">${catName}</button>`;
    }
    document.getElementById("add-problem-field").innerHTML = btnString;
}
function toggle(id) {
    const x = document.getElementById(id);
    if (x != null) {
        if (x.style.display === "none") {
            x.style.display = "block";
        }
        else {
            x.style.display = "none";
        }
    }
}
function getCategoriesContainers() {
    const mList = new Map();
    for (const [k, v] of jsonMap) {
        mList.set(`${k}-btns-container`, { vsize: v.length, catnum: { cat: k, num: 0 } });
    }
    return mList;
}
function getCategoriesNumbersContainers() {
    const mList = new Map();
    for (let h of jsonMap.keys()) {
        const inList = jsonMap.get(h);
        if (inList != null) {
            for (let m of inList.keys()) {
                mList.set(`${h}-${m + 1}-btns-container`, { cat: h, num: m + 1 });
            }
        }
    }
    return mList;
}
function addPlusMinusButtons(element, catnum) {
    if (element != null) {
        element.innerHTML = `
                      <button class="mini-button"
                          onclick="replaceForm('${catnum.cat}',
                                '${catnum.num}','${catnum.cat}-${catnum.num}-show')">+
                      </button>
                      <button class="mini-button">-</button>
                      `;
    }
}
function addCategoriesNumbersButtons() {
    for (const [key, value] of getCategoriesNumbersContainers()) {
        addPlusMinusButtons(document.getElementById(key), value);
    }
}
function addCategoriesButtons() {
    for (const [key, value] of getCategoriesContainers()) {
        if (value.vsize == 1) {
            addPlusMinusButtons(document.getElementById(key), value.catnum);
        }
    }
}
addMessage();
addButtons();
addCategoriesButtons();
addCategoriesNumbersButtons();
