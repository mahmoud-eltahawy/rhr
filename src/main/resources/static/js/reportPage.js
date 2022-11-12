const jsonMap = new Map(Object.entries(JSON.parse(document.getElementById("catsContainer").innerText)))

let url_string = window.location.href
let url = new URL(url_string)
let message = url.searchParams.get("message")
document.getElementById("messager").innerText = message


function replaceForm(machine,number,fieldId){
  document.getElementById(fieldId).innerHTML = `
		<div class="box-container">
			<div class="form-container">
				<h1>${number == 0? machine: machine+' '+number} PROBLEM</h1>
				<form action="/report/problem" method="post">
				<input type="hidden" id="category" name="category" value="${machine}">
				<input type="hidden" id="number" name="number" value="${number}">
				<label name="problems" id="problems">which problem</label>
				<select multiple="multiple" name="problems" id="problems" required>
          ${(function (){
              let options =""
              const problems = JSON.parse(document.getElementById("problemsContainer").innerText)
              for (let p of problems){
                options += `<option value="${p}" >${p}</option>`
              }
              return options
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
`
}

function replaceButtons(catName,fieldId){
  let arr = jsonMap.get(catName)
  if(!arr[0]){
    replaceForm(catName,0,fieldId)
  } else {
    document.getElementById(fieldId).innerHTML = (function(){
      let buttons = ""
      for (let n of arr){
        buttons +=`<button class="cat-button"
                      onclick="replaceForm('${catName}','${n}','${fieldId}')">
                      ${catName} ${n}
                    </button>`
      }
      return buttons
    })()
  }
}

function addButtons(){
  let btnString = ""
  for (let catName of jsonMap.keys()) {
    btnString += `<button class="cat-button"
                              onclick="replaceButtons('${catName}','add-problem-field')">${catName}</button>`
  }
  document.getElementById("add-problem-field").innerHTML = btnString
}

addButtons()

function toggle(id) {
  const x = document.getElementById(id);
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}
