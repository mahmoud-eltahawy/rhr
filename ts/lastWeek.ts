type Day = {
  one  :ShiftId;
  two  :ShiftId;
  three:ShiftId;
}
//string is the date
type days = Map<string,Day>

const year  = localStorage.getItem("target-date-year")
const month = localStorage.getItem("target-date-month")
const day   = localStorage.getItem("target-date-day")

const emp = localStorage.getItem("target-emp")

let weekIdx = 0

function lookingForDay(){
    if(year && month && day){
        return true
    } else {
        return false
    }
}

async function getPageContent(): Promise<days>{
    const formData = new FormData()
    let path = "/get"
    if(lookingForDay()){
        localStorage.removeItem("target-date-year")
        localStorage.removeItem("target-date-month")
        localStorage.removeItem("target-date-day")
        formData.append("year",year!)
        formData.append("month",month!)
        formData.append("day",day!)
        path += "/day"
    }else if(emp){
        localStorage.removeItem("target-emp")
        formData.append("idx",`${weekIdx}`)
        formData.append("name",emp)
       path += "/emp/week"
    } else {
        formData.append("idx",`${weekIdx}`)
        path += "/week"
    }
    return fetch(path,{method: 'POST',body: formData})
        .then(res => res.json())
}

function shiftMember(shift: ShiftId){
    if(shift){
        return `
        <div class="inner-box">
            <h5>${shift.shift}</h5>
            <button onclick="showShift('${shift.id}')"
            class="btn-primary">Go</button>
        </div>
        `
    }
    else {
        return ""
    }
}

const bar = `
	<div class="inner-box">
		<button onclick="nextPage()" class="btn-primary">Next</button>
	</div>
	<div class="inner-box">
		<a href="/"><button class="btn-primary">Home</button></a>
	</div>
	<div class="inner-box">
		<button onclick="prevPage()" class="btn-primary">Prev</button>
	</div>
`

async function makeCoreContent(){
    const days = new Map(Object.entries(await getPageContent()))
    let result = ""
    days.forEach((v,k) => {
        result +=`
        <div>
            <h4>${k}</h4>
        </div>
        <div class="box-container">
            ${shiftMember(v.one)}
            ${shiftMember(v.two)}
            ${shiftMember(v.three)}
        </div>
        `
    })
    document.getElementById("core-content")!.innerHTML = result
    if(!lookingForDay()){
        document.getElementById("top-bar")!.innerHTML = bar
        document.getElementById("bottom-bar")!.innerHTML = bar
    }
}

function nextPage(){
    weekIdx++
    makeCoreContent()
}

function prevPage(){
    if(weekIdx > 0){
        weekIdx--
    }
    makeCoreContent()
}

makeCoreContent()