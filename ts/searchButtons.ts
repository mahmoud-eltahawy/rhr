(function(){
  const allButtons : HTMLCollection = document.getElementsByTagName("button")
  for(let i = 0; i < allButtons.length ; i++){
    const btn : Element = allButtons[i]
    btn.addEventListener("click", function(){
      const btnId : string | null = btn.getAttribute("id")
      if(btnId){
        const actionPlace :HTMLElement | null = document.getElementById("action-place")
        const targetForm :HTMLElement | null = document.getElementById(btnId.slice(0,-4))
        if(targetForm && actionPlace){
          actionPlace.innerHTML = targetForm.innerHTML
        } else {
          throw new Error("target form or action place is null ")
        }
      } else {
        throw new Error("button id is null ")
      }
    })
  }
})()
