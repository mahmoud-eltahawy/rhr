(function(){
  const allButtons :HTMLCollectionOf<HTMLButtonElement> = document.getElementsByTagName("button")
  for(let i = 0; i< allButtons.length ; i++){
    const btn : HTMLButtonElement = allButtons[i]
    btn.addEventListener("click", function(){
      const btnId = btn.getAttribute("id")
      if(btnId){
      const targetElement : HTMLElement | null= document.getElementById(btnId.slice(0,-4))
        if(targetElement){
          if (targetElement.style.display === "none") {
            targetElement.style.display = "block"
          } else {
            targetElement.style.display = "none"
          }
        } else {
          throw new Error(`target with the id ${targetElement} is null`)
        }
      } else {
        throw new Error(`button with the id ${btnId} is null`)
      }
    })
  }
})()