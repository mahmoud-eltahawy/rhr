function addAddingButtons(){
  const jsonMap = new Map(Object.entries(JSON.parse(document.getElementById("catsContainer").innerText)))
  const mainButton = document.getElementById("add-problem-field-btn")
  const mainField = document.getElementById("add-problem-field")

  for (let catName of jsonMap.keys()) {
    mainField.innerHTML += `<button class="cat-button" id="add-${catName}-btn">${catName}</button>`
  }
}

addAddingButtons()
