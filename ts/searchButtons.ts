function actionPlacetoggling(){
  const allButtons = document.getElementsByTagName("button")
  for(let i = 0; i < allButtons.length ; i++){
    allButtons[i].addEventListener("click", function(){
      document.getElementById("action-place")!.innerHTML = document
        .getElementById(allButtons[i].getAttribute("id")!.slice(0,-4))!.innerHTML
    })
  }
}
actionPlacetoggling()
