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
function listProblems(uuid) {
    document.getElementById(uuid + "-problems").innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <form action="/report/add/problem/problems" method="post">
          <input type="hidden" id="id" name="id" value="${uuid}">
          <select multiple="multiple" name="titles" id="titles" required>
            ${(function () {
        let options = "";
        const problems = JSON.parse(document.getElementById("problemsContainer").innerText);
        for (let p of problems) {
            options += `<option value="${p}" >${p}</option>`;
        }
        return options;
    })()}
          </select>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
`;
}
function replaceFlowForm(id) {
    document.getElementById(id).innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <h1> Total Flow record</h1>
          <form action="/report/flow" method="post">
          <label name="machines" id="machines">suspended machines</label>
          <select multiple="multiple" name="machines" id="machines" required>
            ${(function () {
        const mList = [];
        jsonMap.forEach((v, k) => {
            v.forEach(n => {
                mList.push(`<option value="${k}-${n}">${k}${n ? '-' + n : ''}</option>`);
            });
        });
        return mList;
    })()}
          </select>
          <label name="max" id="max">maximum</label>
          <input type="number" id="max" name="max" required>
          <label name="min" id="min">minimum</label>
          <input type="number" id="min" name="min" required>
          <label name="beginTime" id="beginTime">record begin</label>
          <input type="time" id="beginTime" name="beginTime" required>
          <label name="endTime" id="endTime">record end</label>
          <input type="time" id="endTime" name="endTime" required>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
`;
}
function replaceTempForm() {
    document.getElementById("record-section-add-field").innerHTML = `
<h1>hello record form</h1>
`;
}
function replaceNoteForm() {
    document.getElementById("record-section-add-field").innerHTML = `
<h1>hello record form</h1>
`;
}
function addPlusButtons(element, catnum) {
    if (element) {
        element.innerHTML += `
                      <button class="mini-button"
                          onclick="replaceForm('${catnum.cat}',
                                '${catnum.num}','${catnum.cat}-${catnum.num}-show')">+
                      </button>
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
            addPlusButtons(document.getElementById(key), value.catnum);
        }
    }
    const cn = getCategoriesNumbersContainers();
    for (const [key, value] of cn) {
        addPlusButtons(document.getElementById(key), value);
    }
})();
