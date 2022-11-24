
const countDownDate : number = new Date("Nov 30, 2022 00:00:00").getTime()
const x : number = setInterval(function() {
  const distance : number = countDownDate - new Date().getTime()

  const days    : number = Math.floor(distance / (1000 * 60 * 60 * 24))
  const hours   : number = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
  const minutes : number = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60))
  const seconds : number = Math.floor((distance % (1000 * 60)) / 1000)

  document.getElementById("time")!.innerHTML = days + "d " + hours + "h "
  + minutes + "m " + seconds + "s "

  if (distance < 0) {
    clearInterval(x)
    document.getElementById("message")!.innerHTML = "DEADLINE MISSED"
  }
}, 1000)
