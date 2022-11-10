const jsonMap = new Map(Object.entries(JSON.parse(document.getElementById("catsContainer").innerText)))

function replaceForm(machine,number){
  let title = "";
  if(number == 0){
    title = machine;
  } else {
    title = machine + " " + number + " PROBLEM"
  }
  document.getElementById("add-category-field").innerHTML = `
		<div class="box-container">
			<div class="inner-box">
				<h1>${title}</h1>
				<form action="#" th:action="@{/report/problem}" method="post">
				<input type="hidden" id="machine" name="machine" value="${machine}-${number}">
				<label name="problems" id="problems">which problem</label><br>
				<select multiple="multiple" name="problems" id="problems" required>
					<option th:each="" th:value="" th:text="">title</option>
				</select><br>
				<label name="beginTime" id="beginTime">Problem begin</label>
				<input type="time" id="beginTime" name="beginTime" required><br>
				<label name="endTime" id="endTime">Problem end</label>
				<input type="time" id="endTime" name="endTime"   required><br>
				<button type="submit">Submit</button>
				</form>
			</div>
		</div>
`
}

function replaceButtons(catName){
  let arr = jsonMap.get(catName)
  if(arr[0] == 0){
    replaceForm(catName,0)
  } else {
    document.getElementById("add-category-field").innerHTML = (function(){
      let buttons = ""
      for (let n of arr){
        buttons +=`<button class="cat-button"
                      onclick="replaceForm('${catName}','${n}')">
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
                              onclick="replaceButtons('${catName}')">${catName}</button>`
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
