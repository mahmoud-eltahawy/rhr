function toggle(id) {
  const x = document.getElementById(id);
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

const allButtons = document.getElementsByTagName("button")

for(let i = 0; i< allButtons.length ; i++){
  allButtons[i].addEventListener("click", function(){
    toggle(allButtons[i].getAttribute("id").slice(0,-4))
  })
}
