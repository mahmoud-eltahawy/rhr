const promiseMap :Promise<Map<string,number[]>> =
  fetch("/fetch/report/standard/categories/numbers/mapping",{method : 'GET'})
  .then(res => {return res.json()})

const names : Promise<string[]> = fetch("/fetch/report/all/usernames",{method : 'GET'})
  .then(res => {return res.json()})


async function replaceForm(machine : string ,number: number ,fieldId: string){
  try{
    const fieldDiv = document.getElementById(fieldId)
    const begin = await shiftBegin()
    const end = await shiftEnd()
    if(fieldDiv){
      fieldDiv.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <h1>${number == 0? machine: machine+' '+number} PROBLEM</h1>
            <form action="/report/problem" method="post">
            <input type="hidden" id="category" name="category" value="${machine}">
            <input type="hidden" id="number" name="number" value="${number}">
            <label name="problems" id="problems">which problem</label>
            <select multiple="multiple" name="problems" id="problems" required>
              ${(function (){
                  let options =""
                  const problems = JSON.parse(document.getElementById("problemsContainer")!.innerText)
                  for (let p of problems){
                    options += `<option value="${p}" >${p}</option>`
                  }
                  return options
                })()}
            </select>
            <label name="beginTime" id="beginTime">Problem begin</label>
            <input type="time" id="beginTime" name="beginTime" min="${begin}" max="${end}" required>
            <label name="endTime" id="endTime">Problem end</label>
            <input type="time" id="endTime" name="endTime" min="${begin}" max="${end}" required>
            <button type="submit">Submit</button>
            </form>
          </div>
        </div>
    `
    } else {
      throw new Error(fieldDiv + " does not exist")
    }
  } catch (err){
    console.log(err)
  }
}

async function replaceButtons(catName: string ,fieldId: string){
  try{
    const jsonMap =new Map(Object.entries(await promiseMap))
    const arr : number[] | undefined = jsonMap.get(catName)
    if(arr){
      if(arr.length === 1){
          replaceForm(catName,arr[0],fieldId)
        } else if(arr.length > 1) {
          const fieldDiv = document.getElementById(fieldId)
          if(fieldDiv){
            fieldDiv.innerHTML = (function(){
            let buttons = ""
            for (let n of arr){
              buttons +=`<button class="cat-button"
                            onclick="replaceForm('${catName}','${n}','${fieldId}')">
                            ${catName} ${n}
                          </button>`
            }
            return buttons
            })()
          }
      }
    }
  } catch(err){
    console.log(err)
  }
}

function toggle(id :string) {
  const x = document.getElementById(id)
  if(x){
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }
}

async function getCategoriesContainers() : Promise<Map<string,{vsize: number,catnum: {cat: string, num: number}}>>{
  try{
    const jsonMap = new Map(Object.entries(await promiseMap))
    const mList = new Map()
    for (const [k,v] of jsonMap) {
      mList.set(`${k}-btns-container`,{vsize: v.length, catnum:{cat: k,num: 0}})
    }
    return Promise.resolve(mList)
  } catch(err){
    console.log(err)
    return Promise.resolve(new Map<string,{vsize: number,catnum: {cat: string, num: number}}>)
  }
}

async function getCategoriesNumbersContainers() :Promise<Map<string,{cat: string, num: number}>> {
  try{
    const jsonMap =new Map(Object.entries(await promiseMap))
    const mList : Map<string,{cat: string, num: number}> = new Map()
    for (const [k,v] of jsonMap) {
      if(v){
        for(let m of v){
          mList.set(`${k}-${m+1}-btns-container`,{cat : k, num: m+1})
        }
      }
    }
    return mList
  } catch(err){
    console.log(err)
    return Promise.resolve(new Map<string,{cat: string, num: number}>)
  }
}

function listProblems(uuid : string){
  document.getElementById(uuid+"-problems")!.innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <form action="/report/add/problem/problems" method="post">
          <input type="hidden" id="id" name="id" value="${uuid}">
          <select multiple="multiple" name="titles" id="titles" required>
            ${(function (){
                let options =""
                const problems = JSON.parse(document.getElementById("problemsContainer")!.innerText)
                for (let p of problems){
                  options += `<option value="${p}" >${p}</option>`
                }
                return options
              })()}
          </select>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
`
}

async function listMachines(uuid : string){
  try{
    const jsonMap : Map<string,number[]>  = new Map(Object.entries(await promiseMap))
    document.getElementById(uuid+"-machines")!.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <form action="/report/add/flow/machines" method="post">
            <input type="hidden" id="id" name="id" value="${uuid}">
            <select multiple="multiple" name="machines" id="machines" required>
              ${
                (function(){
                  const mList : string[] = []
                  jsonMap.forEach((v,k) =>{
                    v.forEach(n =>{
                      mList.push(`<option value="${k}-${n}">${k}${n?'-'+n:''}</option>`)
                    })
                  })
                  return mList;
                })()
              }
            </select>
            <button type="submit">Submit</button>
            </form>
          </div>
        </div>
    `
  } catch(err){
    console.log(err)
  }
}

async function listEmployees(){
  try{
    const usernames : string[] = await names
    document.getElementById("employee-section")!.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <form action="/report/emp/" method="post">
            <select name="emp" id="emp" required>
              ${
                (function(){
                  const mList : string[] = []
                    usernames.forEach(n =>{
                      mList.push(`<option value="${n}">${n}</option>`)
                    })
                  return mList;
                })()
              }
            </select>
            <button type="submit">Submit</button>
            </form>
          </div>
        </div>
    `
  } catch (err){
    console.log(err)
  }
}


async function replaceFlowForm(id : string){
  try{
    const jsonMap : Map<string,number[]> =new Map(Object.entries( await promiseMap))
    const minTime = flowMinTime();
    const maxTime = shiftEnd();   
    document.getElementById(id)!.innerHTML =`
        <div class="box-container">
          <div class="form-container">
            <h1> Total Flow record</h1>
            <form action="/report/flow" method="post">
            <label name="machines" id="machines">suspended machines</label>
            <select multiple="multiple" name="machines" id="machines" required>
              ${
                (function(){
                  const mList : string[] = []
                  jsonMap.forEach((v,k) =>{
                    v.forEach(n =>{
                      mList.push(`<option value="${k}-${n}">${k}${n?'-'+n:''}</option>`)
                    })
                  })
                  return mList;
                })()
              }
            </select>
            <label name="max" id="max">maximum</label>
            <input type="number" id="max" name="max" required>
            <label name="min" id="min">minimum</label>
            <input type="number" id="min" name="min" required>
            <label name="beginTime" id="beginTime">record begin</label>
            <input type="time" id="beginTime" name="beginTime" value="${minTime}" readonly>
            <label name="endTime" id="endTime">record end</label>
            <input type="time" id="endTime" name="endTime" min="${minTime}" max="${maxTime}" required>
            <button type="submit">Submit</button>
            </form>
          </div>
        </div>
    `
  } catch(err){
    console.log(err)
  }
}

function flowMinTime(){
  const endTimesContainers  = document.getElementsByName('flow-end-time')
  if(endTimesContainers[endTimesContainers.length - 1].innerText == null){
    return shiftBegin()
  } else {
    return endTimesContainers[endTimesContainers.length - 1].innerText.slice(0,5)
  }
}

async function shiftBegin(){
  try{
    const strTime :string = await fetch("/fetch/report/current/shift/begin/time",{method : 'GET'})
    .then(res => {return res.json()})
    console.log(strTime)
    return strTime.slice(0,5)
  } catch(err){
    console.log(err)
  }
}

async function shiftEnd(){
  try{
    const newHour  = await shiftBegin()
    const newHourNum = +newHour!.slice(0,2) + 8
    if(newHourNum < 10){
      return '0'+newHourNum+':00'
    }
    return newHourNum+':00'
  } catch(err){
    console.log(err)
  }
}

async function replaceTempForm(id: string){
  try{
    const jsonMap: Map<string,number[]> = new Map(Object.entries(await promiseMap))
    
    document.getElementById(id)!.innerHTML =`
        <div class="box-container">
          <div class="form-container">
            <h1> Temperature record</h1>
            <form action="/report/temp" method="post">
            <label name="machine" id="machine">target machine</label>
            <select name="machine" id="machine" required>
              ${
                (function(){
                  const mList : string[] = []
                  jsonMap.forEach((v,k) =>{
                    if(k == 'KILEN' || k == 'PROJECT'){
                      v.forEach(n =>{
                        mList.push(`<option value="${k}-${n}">${k}${n?'-'+n:''}</option>`)
                      })
                    }
                  })
                  return mList;
                })()
              }
            </select>
            <label name="max" id="max">maximum</label>
            <input type="number" id="max" name="max" required>
            <label name="min" id="min">minimum</label>
            <input type="number" id="min" name="min" required>
            <button type="submit">Submit</button>
            </form>
          </div>
        </div>
    `
  } catch(err){
    console.log(err)
  }
}

function replaceNoteForm(id :string){
  document.getElementById(id)!.innerHTML =`
      <div class="box-container">
        <div class="form-container">
          <form action="/report/note" method="post">
          <label for="note">NOTE</label>
          <textarea id="note" name="note" rows="4"
                cols="50" class="short-important-p"
                style="width:80%; margin:5%; color:white;">...</textarea>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
`
}

function addPlusButtons(element : HTMLElement,catnum : {cat: string, num : number}){
  if(element){
    element.innerHTML += `
                      <button class="mini-button"
                          onclick="replaceForm('${catnum.cat}',
                                '${catnum.num}','${catnum.cat}-${catnum.num}-show')">+
                      </button>
                      `
  }
}

function addMessage(){
    const message : string | null = new URL(window.location.href).searchParams.get("message")
    if(message) {
      document.getElementById("messager")!.innerText = message
    } else {
      document.getElementById("messager")!.style.display = "none"
    }
}
async function messionsOnLaunch(){
  try{
    let jsonMap =new Map(Object.entries(await promiseMap))
    //add buttons
    let btnString = ""
    for (let catName of jsonMap.keys()) {
      btnString += `<button class="cat-button"
                                onclick="replaceButtons('${catName}','add-problem-field')">${catName}</button>`
    }
    document.getElementById("add-problem-field")!.innerHTML = btnString
    // show last flow record delete button
    const arr = document.getElementsByName('flow-end-time')
    if(arr.length > 0){
      document.getElementById(arr[arr.length - 1].id.slice(0,-4) +'-btn')!.style.display = 'block'
    }

    const cc =Object.entries(await getCategoriesContainers())
    for(const [key,value] of cc){
      if(value.vsize == 1){
        addPlusButtons(document.getElementById(key)!,value.catnum)
      }
    }
    const cn =Object.entries(await getCategoriesNumbersContainers())
    for(const [key,value] of cn){
      addPlusButtons(document.getElementById(key)!,value)
    }
  } catch(err){
    console.log(err)
  }
}

addMessage()
messionsOnLaunch()