function togglingButtons(){
  const allButtons = document.getElementsByTagName("button")

  for(let i = 0; i< allButtons.length ; i++){
    allButtons[i].addEventListener("click", function(){
      const x = document.getElementById(allButtons[i].getAttribute("id")!.slice(0,-4))
      if( x != null ){
        if (x.style.display === "none") {
          x.style.display = "block"
        } else {
          x.style.display = "none"
        }
      }
    })
  }
}

 togglingButtons()
