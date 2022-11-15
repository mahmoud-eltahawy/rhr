"use strict";
const jsonMap = new Map(Object
    .entries(JSON.parse(document.getElementById("catsContainer").innerText)));
function replaceForm(machine, number, fieldId) {
    const fieldDiv = document.getElementById(fieldId);
    if (fieldDiv) {
        fieldDiv.innerHTML = `
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
    else {
        throw new Error(fieldDiv + " does not exist");
    }
}
function replaceButtons(catName, fieldId) {
    let arr = jsonMap.get(catName);
    if (arr) {
        if (arr.length === 1) {
            replaceForm(catName, arr[0], fieldId);
        }
        else if (arr.length > 1) {
            const fieldDiv = document.getElementById(fieldId);
            if (fieldDiv) {
                fieldDiv.innerHTML = (function () {
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
}
function toggle(id) {
    const x = document.getElementById(id);
    if (x) {
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
        if (inList) {
            for (let m of inList.keys()) {
                mList.set(`${h}-${m + 1}-btns-container`, { cat: h, num: m + 1 });
            }
        }
    }
    return mList;
}
function addPlusMinusButtons(element, catnum) {
    if (element) {
        element.innerHTML = `
                      <button class="mini-button"
                          onclick="replaceForm('${catnum.cat}',
                                '${catnum.num}','${catnum.cat}-${catnum.num}-show')">+
                      </button>
                      <button class="mini-button">-</button>
                      `;
    }
}
if (true) {
    //show message
    const message = new URL(window.location.href).searchParams.get("message");
    if (message) {
        document.getElementById("messager").innerText = message;
    }
    else {
        document.getElementById("messager").style.display = "none";
    }
    //add buttons
    let btnString = "";
    for (let catName of jsonMap.keys()) {
        btnString += `<button class="cat-button"
                              onclick="replaceButtons('${catName}','add-problem-field')">${catName}</button>`;
    }
    document.getElementById("add-problem-field").innerHTML = btnString;
}
(function () {
    const cc = getCategoriesContainers();
    for (const [key, value] of cc) {
        if (value.vsize == 1) {
            addPlusMinusButtons(document.getElementById(key), value.catnum);
        }
    }
    const cn = getCategoriesNumbersContainers();
    for (const [key, value] of cn) {
        addPlusMinusButtons(document.getElementById(key), value);
    }
})();
