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
  const button = allButtons[i]
  button.addEventListener("click", function(){
    toggle(button.getAttribute("id").slice(0,-4))
  })
}
