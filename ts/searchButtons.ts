const standard :Promise<Map<category,number[]>> =(async function() {
  try{
    const result: Map<category,number[]>= new Map()
    const stringMap :Map<string,string> = new Map(Object.entries(
      await fetch("/fetch/report/standard/categories/numbers/mapping")
      .then(res => res.json())))
    stringMap.forEach((v,k) => result.set(JSON.parse(k),JSON.parse(v)))
    return result
  }catch(err){
    console.log(err)
    return new Map()
  }
})()

const emps : Promise<string[]> = fetch("/fetch/report/all/usernames")
.then(res => {return res.json()})

const titles = fetch("/fetch/report/all/problems/titles")
.then(res => {return res.json()})

function putShift(){
  document.getElementById("action-place")!.innerHTML = `
    <div>
      <h1>insert Date and Order</h1>
      <form onsubmit="searchShift(); return false;">
        <input type="date" id="date-id" required="required">
        <select required="required" id="order-id">
            <option value="FIRST"  selected>first shift</option>
            <option value="SECOND"         >second shift</option>
            <option value="THIRD"          >third shift</option>
        </select>
        <button type="submit">Submit</button>
      </form>
    </div>
    `
}
async function searchShift(){
  const date = document.getElementById("date-id") as HTMLInputElement
  const order = document.getElementById("order-id") as HTMLSelectElement
  const dateArr = date.value.split("-")
  const year = dateArr[0]
  const month = dateArr[1]
  const day = dateArr[2]
  const formData = new FormData()
  formData.append("order",order.value)
  formData.append("year",year)
  formData.append("month",month)
  formData.append("day",day)
  const id = await fetch("/shift/id",
  {method: 'POST', body:formData})
  .then(res => res.json())
  if(id){
    showShift(id)
  }
}
function putDay(){
  document.getElementById("action-place")!.innerHTML = `
      <div>
        <h1>insert Date</h1>
        <form onsubmit="searchDay(); return false;">
                <input type="date" id="date-id" required="required">
          <button type="submit">Submit</button>
        </form>
      </div>
    `
}

function searchDay(){
  const dateInput = document.getElementById("date-id") as HTMLInputElement
  const arr = dateInput.value.split("-")
  localStorage.setItem("target-date-year" ,arr[0])
  localStorage.setItem("target-date-month",arr[1])
  localStorage.setItem("target-date-day"  ,arr[2])
  location.pathname = "/show/last/week"
}

async function putEmployee(){
  document.getElementById("action-place")!.innerHTML = `
    <div>
        <h1>insert username</h1>
        <form onsubmit="searchEmployee(); return false;">
            <select required="required" id="username-id">
            ${await (async ()=> {
                  const names = await emps
                  let opts = ""
                  names.forEach((name) =>{
                    opts += `<option value="${name}">${name}</option>`
                  })
                  return opts;
              })()}
            </select>
            <button type="submit">Submit </button>
            </div>
        </form>
    </div>
    `
}

function searchEmployee(){
  const nameInput = document.getElementById("username-id") as HTMLSelectElement
  localStorage.setItem("target-emp",nameInput.value)
  location.pathname = "/show/last/week"
}