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
        <form action="/show/day">
                <input type="date" name="date" required="required">
          <button type="submit">Submit</button>
        </form>
      </div>
    `
}
async function putProblem(){
  document.getElementById("action-place")!.innerHTML = `
    <div>
      <h1>insert Problem Name</h1>
      <form action="/show/problem" method="post">
              <select required="required" name="problem-title">
              ${await (async () => {
                let opts = ""
                for(const t of await titles){
                  opts += `
                    <option value="${t}">${t}</option>
                  `
                }
                return opts
              })()}
              </select>
              <input type="hidden" id="page" name="page" value="0">
              <button type="submit">Submit </button>
      </form>
    </div>
    `
}
async function putMachine(){
  document.getElementById("action-place")!.innerHTML = `
  	<div>
      <h1>insert Machine Name</h1>
      <form action="/show/machine" method="post">
                  <select required="required" name="machine">
                  ${await (async ()=> {
                        const jsonMap : Map<category,number[]> = await standard
                        let opts = ""
                        jsonMap.forEach((numbers,category) =>{
                          numbers.forEach(number =>{
                            opts += `<option value="${category.name}-${number}">${category.name}${number?'-'+number:''}</option>`
                          })
                        })
                        return opts;
                    })()}
                  </select>
                  <input type="hidden" id="page" name="page" value="0">
                  <button type="submit">Submit </button>
      </form>
    </div>
    `
}

async function putEmployee(){
  document.getElementById("action-place")!.innerHTML = `
    <div>
        <h1>insert username</h1>
        <form action="/show/emp" method="post">
            <select required="required" id="username" name="username">
            ${await (async ()=> {
                  const names = await emps
                  let opts = ""
                  names.forEach((name) =>{
                    opts += `<option value="${name}">${name}</option>`
                  })
                  return opts;
              })()}
            </select>
            <input type="hidden" name="month" value="0">
            <button type="submit">Submit </button>
            </div>
        </form>
    </div>
    `
}