const promiseMap :Promise<Map<string,number[]>> =Promise.resolve(new Map(Object
  .entries(JSON.parse(document.getElementById("catsContainer")!.innerText))))
const names : string[] = JSON.parse(document.getElementById("namesContainer")!.innerText)

function replaceForm(machine : string ,number: number ,fieldId: string){
  const fieldDiv = document.getElementById(fieldId)
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
          <input type="time" id="beginTime" name="beginTime" min="${shiftBegin()}" max="${shiftEnd()}" required>
          <label name="endTime" id="endTime">Problem end</label>
          <input type="time" id="endTime" name="endTime" min="${shiftBegin()}" max="${shiftEnd()}" required>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
  `
  } else {
    throw new Error(fieldDiv + " does not exist")
  }
}

async function replaceButtons(catName: string ,fieldId: string){
  try{
    const jsonMap = await promiseMap
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
    const jsonMap = await promiseMap
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
    const jsonMap = await promiseMap
    const mList : Map<string,{cat: string, num: number}> = new Map()
    for (let h of jsonMap.keys()) {
      const inList : number[] | undefined = jsonMap.get(h)
      if(inList){
        for(let m of inList.keys()){
          mList.set(`${h}-${m+1}-btns-container`,{cat : h, num: m+1})
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
    const jsonMap = await promiseMap
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

function listEmployees(){
  document.getElementById("employee-section")!.innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <form action="/report/emp/" method="post">
          <select name="emp" id="emp" required>
            ${
              (function(){
                const mList : string[] = []
                  names.forEach(n =>{
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
}


async function replaceFlowForm(id : string){
  try{
    const jsonMap = await promiseMap
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
            <input type="time" id="beginTime" name="beginTime" value="${flowMinTime()}" readonly>
            <label name="endTime" id="endTime">record end</label>
            <input type="time" id="endTime" name="endTime" min="${flowMinTime()}" max="${shiftEnd()}" required>
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
  const endTimes : string[] = []
  document.getElementsByName('flow-end-time').forEach((e)=> endTimes.push(e.innerText))
  if(endTimes[endTimes.length - 1] == null){
    return shiftBegin()
  } else {
    return endTimes[endTimes.length - 1].slice(0,5)
  }
}

function shiftBegin(){
  const strTime = document.getElementById('beginTime')!.innerText
  return strTime.slice(0,5)
}

function shiftEnd(){
    const strTime = document.getElementById('beginTime')!.innerText
    const newHour  = +strTime.slice(0,2) + 8
    if(newHour < 10){
      return '0'+newHour+':00'
    }
    return newHour+':00'
}

async function replaceTempForm(id: string){
  try{
    const jsonMap = await promiseMap
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

async function messionsOnLaunch(){
  try{
    const jsonMap = await promiseMap
    //show message
    const message : string | null = new URL(window.location.href).searchParams.get("message")
    if(message) {
      document.getElementById("messager")!.innerText = message
    } else {
      document.getElementById("messager")!.style.display = "none"
    }
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

    const cc = await getCategoriesContainers()
    for(const [key,value] of cc){
      if(value.vsize == 1){
        addPlusButtons(document.getElementById(key)!,value.catnum)
      }
    }
    const cn = await getCategoriesNumbersContainers()
    for(const [key,value] of cn){
      addPlusButtons(document.getElementById(key)!,value)
    }
  } catch(err){
    console.log(err)
  }
}

messionsOnLaunch()