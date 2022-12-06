function showShift(id: string){
    localStorage.setItem("shiftIdId",id)
    location.href=`http://${location.host}/show/shift`
}