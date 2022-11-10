const jsonMap = new Map(Object.entries(JSON.parse(document.querySelector("#catsContainer").innerText)))

function addAddingButtons(){
  const mainField = document.querySelector("#add-problem-field")

  for (let catName of jsonMap.keys()) {
    mainField.innerHTML += `<button class="cat-button" id="add-${catName}-btn">${catName}</button>`
  }
}

function addingButtons(){
  for (let catName of jsonMap.keys()) {
    document.querySelector("#"+"add-"+catName+"-btn").addEventListener("click",function(){
      document.querySelector("#add-category-field").innerHTML = (function(){
        let buttons = ""
        for (let n of jsonMap.get(catName)){
          buttons +=`<button class="cat-button" id="add-${catName}-${n}-btn">${catName}-${n}</button>`
        }
        return buttons;
      })();
    })
  }
}

addAddingButtons()
addingButtons()
