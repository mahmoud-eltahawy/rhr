function addAddingButtons(){
  const jsonMap = new Map(Object.entries(JSON.parse(document.querySelector("#catsContainer").innerText)))
  const mainField = document.querySelector("#add-problem-field")

  for (let catName of jsonMap.keys()) {
    mainField.innerHTML += `<button class="cat-button" id="add-${catName}-btn">${catName}</button>`
  }
}

addAddingButtons()
