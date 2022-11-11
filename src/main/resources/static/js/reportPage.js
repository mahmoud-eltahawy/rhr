const jsonMap = new Map(Object.entries(JSON.parse(document.getElementById("catsContainer").innerText)))

function replaceForm(machine,number,fieldId){
  let title = "";
  if(number == 0){
    title = machine;
  } else {
    title = machine + " " + number + " PROBLEM"
  }

  let options =""
  const problems = JSON.parse(document.getElementById("problemsContainer").innerText)
  for (let p of problems){
    options += `<option value="${p}" >${p}</option>`
  }
  document.getElementById(fieldId).innerHTML = `
		<div class="box-container">
			<div class="form-container">
				<h1>${title}</h1>
				<form action="/report/problem" method="post">
				<input type="hidden" id="category" name="category" value="${machine}">
				<input type="hidden" id="number" name="number" value="${number}">
				<label name="problems" id="problems">which problem</label>
				<select multiple="multiple" name="problems" id="problems" required>
          ${options}
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
  if(arr[0] == 0){
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
  const mainField = document.getElementById("add-problem-field")

  for (let catName of jsonMap.keys()) {
    mainField.innerHTML += `<button class="cat-button"
                              onclick="replaceButtons('${catName}','add-category-field')">${catName}</button>`
  }
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
