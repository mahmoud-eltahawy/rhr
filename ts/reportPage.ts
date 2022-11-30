const promiseMap :Promise<Map<string,number[]>> =
  fetch("/fetch/report/standard/categories/numbers/mapping"
  ,{method : 'GET'}).then(res => {return res.json()})

const names : Promise<string[]> = fetch("/fetch/report/all/usernames"
  ,{method : 'GET'}).then(res => {return res.json()})

const problemsTitles = fetch("/fetch/report/all/problems/titles"
  ,{method : 'GET'}).then(res => {return res.json()})


async function problemsOptions (){
  try{
    let options =""
    const problems = await problemsTitles
    for (let p of problems){
      options += `<option value="${p}" >${p}</option>`
    }
    return options
  } catch (err){
    console.log(err)
  }
}

function elementListNewMember(machine: string,number: string, pd :{
      id: string;shiftId:string;
      problems:[{title: string; descripion: string;}];
      machine:{id: string; category: string; number: number;};
      beginTime: string;endTime: string;}){
  document.getElementById(`${machine}-${number}-problems-list`)!
    .innerHTML += `
      <tr id="${pd.id}-member">
        <td>
            <button 
                onclick="deleteCategoryProblem('${pd.id}','${pd.id}-member')"
                type="submit"
                class="mini-button"
                style="width: 100%"
                > - </button>
        </td>
        <td>${pd.beginTime}</td>
        <td>${pd.endTime}</td>
        <td id="${pd.id}-problems">
          <ol type="1" id="${pd.id}-problems-list">
            ${(function() : string{
              let result =""
              pd.problems.forEach(p =>{
                result += `
                  <li id="${pd.id}-${p.title}">
                    <button
                    onclick="deleteProblemTitle('${pd.id}','${p.title}','${pd.id}-${p.title}')"
                    style="display:inline-block;">-</button>
                    <span style="display:inline-block;">${p.title}</span>
                  </li>
                `
              })
              return result
            })()}
            <li>
              <button 
                onclick="listProblems('${pd.id}')"
                class="mini-button"
              >+</button>
            </li>
          </ol>
        </td>
      </tr>
    `
}

async function deleteCategoryProblems(machine: string,number: string,fieldId: string) {
  try{
    const formData = new FormData()
    formData.append('cat',machine)
    formData.append('num', number)

    const result : boolean = await fetch("/fetch/report/remove/machine/problems",
      {method: 'POST', body: formData}).then(res => res.json())

    if(result){
      document.getElementById(fieldId)!.innerHTML = ""
      alert('problems deleted successfully')
    }
  } catch(err){
    console.log(err)
  }
}

async function deleteProblemTitle(id: string,title: string,fieldId: string) {
  try{
    const formData = new FormData()
    formData.append('id',id)
    formData.append('title', title)

    const result : boolean = await fetch("/fetch/report/remove/problem/problem",
      {method: 'POST', body: formData}).then(res => res.json())

    if(result){
      document.getElementById(fieldId)!.remove()
      alert('title removed successfully')
    }
  } catch(err){
    console.log(err)
  }
}

async function deleteCategoryProblem(id : string,fieldId: string) {
  try{
    const formData = new FormData()
    formData.append('id',id)

    const result : boolean = await fetch("/fetch/report/remove/problem",
      {method: 'POST', body: formData}).then(res => res.json())

    if(result){
      document.getElementById(fieldId)!.innerHTML = ""
    }
  } catch(err){
    console.log(err)
  }
}

async function removeFlow(id : string){
  try{
    const result = await fetch("/fetch/report/remove/flow")
      .then(res => res.json())
    if(result){
      document.getElementById(id+"-record")!.remove()
      addDeleteFlowRecord()
    }
  } catch(err){
    console.log(err)
  }
}

async function saveNewProblemProblems(id : string) {
  try{
    const titles = document.getElementById(id+"-titles") as HTMLSelectElement
    const formData = new FormData()
    formData.append('id',id)
    formData.append('titles' , (function(){
        const chosenProblems: string[] = []
        for(let i = 0; i < titles.options.length; i++){
          if(titles.options[i].selected){
            chosenProblems.push(titles.options[i].value)
          }
        }
        return chosenProblems.toString()
      })())

      const result : string[] = await fetch("/fetch/report/add/problem/problems",
      {method: 'POST', body: formData}).then(res => res.json())

      restoreContent(id+"-problems-list")

     if(result.length != 0){
        const target = document.getElementById(id+"-problems-list")
        result.forEach(t => {
          target!.innerHTML += `
            <li id="${id}-${t}">
              <button
              onclick="deleteProblemTitle('${id}','${t}','${id}-${t}')"
              style="display:inline-block;">-</button>
              <span style="display:inline-block;">${t}</span>
            </li>
          `
        })
     }
  } catch(err){
    console.log(err)
  }
}

async function listProblems(uuid : string){
  const target = document.getElementById(uuid+"-problems-list")
  nativeContentMap.set(uuid+"-problems-list",target!.innerHTML)
  target!.innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <form onsubmit=" saveNewProblemProblems('${uuid}'); return false;" action="/report/add/problem/problems" method="post">
          <button onclick="restoreContent('${uuid}-problems-list')" style="width:6%; display:block; padding: 0px; color:red;">X</button>
          <input type="hidden" id="id" name="id" value="${uuid}">
          <select multiple="multiple" name="titles" id="${uuid}-titles" required>
            ${await problemsOptions()}
          </select>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    `
}

async function saveProblemRequest(machine: string,number: string,fieldId: string){
  try{
    const problems = document.getElementById(`${machine}-${number}-form-problems`) as HTMLSelectElement
    const begin = document.getElementById(`${machine}-${number}-form-beginTime`) as HTMLInputElement
    const end = document.getElementById(`${machine}-${number}-form-endTime`) as HTMLInputElement
    const formData = new FormData()
    formData.append('category' , machine)
    formData.append('number' , number)
    formData.append('problems' , (function(){
        const chosenProblems: string[] = []
        for(let i = 0; i < problems.options.length; i++){
          if(problems.options[i].selected){
            chosenProblems.push(problems.options[i].value)
          }
        }
        return chosenProblems.toString()
      })())
    formData.append('beginTime' , begin.value)
    formData.append('endTime' , end.value)
    const pd = await fetch("/fetch/report/add/machine/problem"
    ,{method:'POST',body: formData})
    .then(res => res.json());
    restoreContent(fieldId)
    elementListNewMember(machine,number,pd)
  } catch(err){
    console.log(err)
  }
}

function flowListNewMember(
  t :{
    id:string;
    shiftId:string;
    suspendedMachines: [{id:string;
                        category:string;
                        number: number;
                      }];
    minFlow: number;
    maxFlow: number;
    caseBeginTime: string;
    caseEndTime: string;
  }){
  document.getElementById("flow-records-list")!
    .innerHTML += `
    <tr id="${t.id}-record">
      <td>
        {
        <button
            id="${t.id}-btn"
            onclick="removeFlow('${t.id}')"
            type="submit"
            class="mini-button"
            style="width: 100%"
        >-</button>
        }
      </td>
      <td id="${t.id}-machines">
        <ul>
          ${(function(){
            let result = ""
            t.suspendedMachines.forEach(m =>{
              result += `
              <li>
                <span>
                  <a href="/report/remove/flow/machine/?fid=${t.id}&machine=${m.category}-${m.number}">
                    <button class="mini-button">-</button>
                  </a>
                </span>
                <span>${m.category}-${m.number}</span>
              </li>
              `
            })
            return result
          })()}
          <li><button 
            onclick="listMachines('${t.id}')"
            class="mini-button"
          >+</button></li>
        </ul>
      </td>
      <td>${t.maxFlow}</td>
      <td>${t.minFlow}</td>
      <td>${t.caseBeginTime}</td>
      <td id="${t.id}-end" name="flow-end-time">${t.caseEndTime}</td>
    </tr>
    `
}

async function removeAllFlow(){
  try{
    const result = await fetch("/fetch/report/remove/all/flow")
      .then(res => res.json())
      if(result){
        document.getElementById("flow-records-list")!.innerHTML = ""
      }
  } catch(err){
    console.log(err)
  }
}
async function saveFlowRequest(){
  try{
    const machines = document.getElementById("flow-machines-id") as HTMLSelectElement
    const max = document.getElementById("flow-max-id") as HTMLInputElement
    const min = document.getElementById("flow-min-id") as HTMLInputElement
    const begin = document.getElementById("flow-beginTime-id") as HTMLInputElement
    const end = document.getElementById("flow-endTime-id") as HTMLInputElement
    const formData = new FormData()
    formData.append('machines' , (function(){
        const chosenProblems: string[] = []
        for(let i = 0; i < machines.options.length; i++){
          if(machines.options[i].selected){
            chosenProblems.push(machines.options[i].value)
          }
        }
        return chosenProblems.toString()
      })())
    formData.append('max' , max.value)
    formData.append('min' , min.value)
    formData.append('beginTime' , begin.value)
    formData.append('endTime' , end.value)
    const tf = await fetch("/fetch/report/add/flow"
    ,{method:'POST',body: formData})
    .then(res => res.json());
    restoreContent("flow-section")
    flowListNewMember(tf)
    addDeleteFlowRecord()
  } catch(err){
    console.log(err)
  }
}

const nativeContentMap : Map<string,string> = new Map()

function restoreContent(fieldId: string){
  const target = document.getElementById(fieldId)
  const content = nativeContentMap.get(fieldId)
  if(target && content){
    target.innerHTML = content
  }
  nativeContentMap.delete(fieldId)
}
async function replaceForm(machine : string ,number: number ,fieldId: string){
  try{
    const fieldDiv = document.getElementById(fieldId)
    const begin = await shiftBegin()
    const end = await shiftEnd()
    if(fieldDiv){
      nativeContentMap.set(fieldId,fieldDiv.innerHTML)
      fieldDiv.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <h1>${number == 0? machine: machine+' '+number} PROBLEM</h1>
            <button onclick="restoreContent('${fieldId}')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
            <form onsubmit="saveProblemRequest('${machine}','${number}','${fieldId}'); return false;" method="post">
            <label >problems</label>
            <select multiple="multiple" name="problems" id="${machine}-${number}-form-problems" required>
              ${await problemsOptions()}
            </select>
            <label >Problem begin</label>
            <input type="time" id="${machine}-${number}-form-beginTime" name="beginTime" min="${begin}" max="${end}" required>
            <label >Problem end</label>
            <input type="time" id="${machine}-${number}-form-endTime" name="endTime" min="${begin}" max="${end}" required>
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

async function removeFlowMachine(flowId: string,machineId : string) {
  try{
    const formData = new FormData()
    formData.append('flow-id',flowId)
    formData.append('machine-id',machineId)
    const result : boolean = await fetch("/fetch/report/remove/flow/machine",
      {method: 'POST', body: formData}).then(res => res.json())

    if(result){
      document.getElementById(`${machineId}-machine`)!.remove()
    }
  } catch(err){
    console.log()
  }
}

async function addFlowMachines(uuid : string){
  try{
    const machines = document.getElementById(uuid+"-machines-options") as HTMLSelectElement
    const formData = new FormData()
    formData.append('id',uuid)
    formData.append('machines', (function(){
        const chosenMachines: string[] = []
        for(let i = 0; i < machines.options.length; i++){
          if(machines.options[i].selected){
            chosenMachines.push(machines.options[i].value)
          }
        }
        return chosenMachines.toString()
    })())

    const result : [{id:string; category: string; number:number;}] = await fetch("/fetch/report/add/flow/machines",
      {method: 'POST', body: formData}).then(res => res.json())

    restoreContent(uuid+"-machines")
    const target = document.getElementById(uuid+"-machines")    
    result.forEach(m => {
      target!.innerHTML += `
        <li id="${m.id}-machine">
          <button
            onclick="removeFlowMachine('${uuid}','${m.id}')"
            class="mini-button"
          >-</button>
          <span>${m.category}-${m.number}</span>
        </li>
      `
    })
  } catch (err){
    console.log(err)
  }
}

async function listMachines(uuid : string){
  try{
    const target = document.getElementById(uuid+"-machines")
    const jsonMap : Map<string,number[]>  = new Map(Object.entries(await promiseMap))
    nativeContentMap.set(uuid+"-machines",target!.innerHTML)
    target!.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <form onsubmit="addFlowMachines('${uuid}'); return false;">
            <select multiple="multiple" id="${uuid}-machines-options" required>
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

async function replaceFlowForm(){
  try{
    const jsonMap : Map<string,number[]> =new Map(Object.entries( await promiseMap))
    const minTime = await flowMinTime();
    const maxTime = await shiftEnd();
    const target  = document.getElementById("flow-section")
    nativeContentMap.set("flow-section",target!.innerHTML)
    target!.innerHTML =`
        <div class="box-container">
          <div class="form-container">
            <h1> Total Flow record</h1>
            <button onclick="restoreContent('flow-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
            <form onsubmit="saveFlowRequest(); return false;" method="post">
            <label>suspended machines</label>
            <select multiple="multiple" name="machines" id="flow-machines-id" required>
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
            <label>maximum</label>
            <input type="number" id="flow-max-id" name="max" required>
            <label>minimum</label>
            <input type="number" id="flow-min-id" name="min" required>
            <label>record begin</label>
            <input type="time" id="flow-beginTime-id" name="beginTime" value="${minTime}" readonly>
            <label>record end</label>
            <input type="time" id="flow-endTime-id" name="endTime" min="${minTime}" max="${maxTime}" required>
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

async function shiftBegin(){
  try{
    const strTime :string = await fetch("/fetch/report/current/shift/begin/time"
      ,{method : 'GET'}).then(res => {return res.json()})
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

async function removeTemp(machineId : string){
  try{
    const formData = new FormData()
    formData.append("id",machineId)
    const result = await fetch("/fetch/report/remove/temp",
    {method:'POST',body:formData}).then(res => res.json())
    if(result){
      document.getElementById(machineId)!.remove()
    }
  } catch(err){
    console.log(err)
  }
}

async function removeAllTemp(){
  try{
    const result = await fetch("/fetch/report/remove/all/temp")
    if(result){
      document.getElementById("temps-records-list")!.innerHTML = ""
    }
  } catch(err){
    console.log(err)
  }
}

function addTempMember(temp : 
      {id:string;shiftId:string;
       machine:{id: string;category: string;number: number;};
       max: string;min: string;}){

    const ids : string[] = []
    const records = document.getElementById("temps-records-list")
    for(const i of records!.getElementsByTagName("li")){
      ids.push(i.getAttribute("id")!)
    }
    if(ids.includes(temp.machine.id)){
      document.getElementById(temp.machine.id)!.remove()
    }
records!.innerHTML += `
  <li id="${temp.machine.id}">
    <button
      onclick="removeTemp('${temp.machine.id}')"
      class="mini-button"
      style="display:inline-block;width:7%; margin:2%;"
      type="submit"
    >-</button>
    <p
      class="short-important-p"
      style="display:inline-block; width:77%; margin:2%;">
      MACHINE :
      <span>${temp.machine.category}-${temp.machine.number}</span>
      &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
      MAXIMUM : <span>${temp.max}</span>
      &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
      MINIMUM : <span>${temp.min}</span>
    </p>
  </li>
  `
}

async function saveTempRequest(){
  try{
    const machine = document.getElementById("temp-machine-id") as HTMLSelectElement
    const max = document.getElementById("temp-max-id") as HTMLInputElement
    const min = document.getElementById("temp-min-id") as HTMLInputElement
    const formData = new FormData()
    formData.append("machine",machine.value)
    formData.append("max",max.value)
    formData.append("min",min.value)

    const temp  = await fetch("/fetch/report/add/temp"
        ,{method: 'POST', body : formData}).then(res => res.json())

    restoreContent("temp-section")
    addTempMember(temp)
  } catch(err) {
    console.log(err)
  }
}

async function replaceTempForm(){
  try{
    const jsonMap: Map<string,number[]> = new Map(Object.entries(await promiseMap))
    const target = document.getElementById("temp-section")
    nativeContentMap.set("temp-section",target!.innerHTML)
    target!.innerHTML =`
        <div class="box-container">
          <div class="form-container">
            <h1> Temperature record</h1>
            <button onclick="restoreContent('temp-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
            <form onsubmit="saveTempRequest(); return false;">
            <label name="machine" id="machine">target machine</label>
            <select id="temp-machine-id" required>
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
            <label>maximum</label>
            <input type="number" id="temp-max-id" required>
            <label>minimum</label>
            <input type="number" id="temp-min-id" required>
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

function addMessage(){
    const message : string | null = new URL(window.location.href).searchParams.get("message")
    if(message) {
      document.getElementById("messager")!.innerText = message
    } else {
      document.getElementById("messager")!.style.display = "none"
    }
}

function addDeleteFlowRecord(){
  const arr = document.getElementsByName('flow-end-time')
  for(let i = 0; i < arr.length; i++){
    if(i !== arr.length - 1){
      document.getElementById(arr[i].id.slice(0,-4) +'-btn')!.style.display = 'none'
    } else {
      document.getElementById(arr[i].id.slice(0,-4) +'-btn')!.style.display = 'block'
    }
  }
}

addMessage()
addDeleteFlowRecord()