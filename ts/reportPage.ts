const categoryMap :Promise<Map<category,number[]>> =(async function() {
  try{
    const result: Map<category,number[]>= new Map()
    const stringMap :Map<string,number[]> = new Map(Object.entries(
      await fetch("/fetch/report/standard/categories/numbers/mapping"
      ,{method : 'GET'}).then(res => {return res.json()})))
    stringMap.forEach((v,k) => result.set(JSON.parse(k),v))
    return result
  }catch(err){
    console.log(err)
    return new Map()
  }
})()

const names : Promise<string[]> = fetch("/fetch/report/all/usernames"
  ,{method : 'GET'}).then(res => {return res.json()})

const problemsTitles = fetch("/fetch/report/all/problems/titles"
  ,{method : 'GET'}).then(res => {return res.json()})

const nativeContentMap : Map<string,string> = new Map()


type empName = {
  id: string;
  fullName: string;
}

type temp = {
  id: string;
  shiftId: string;
  machine:machine;
  max: number;
  min: number;
}

type note = {
  id: string;
  note: string;
}
type category = {
  name: string;
  hasMachines: boolean;
  hasTemperature: boolean;
}

type flow = {
  id: string;
  shiftId: string;
  suspendedMachines: [machine];
  minFlow: number;
  maxFlow: number;
  caseBeginTime: string;
  caseEndTime: string;
}
type problem = {
  title: string;
  description : string;
}
type machine = {
    id: string;
    category: category;
    number: number;
}

type ShiftId = {
  id: string;
  date: string;
  shift: string;
}

type problemDetail = {
  id: string;
  shiftId: string;
  problems: problem[];
  machine: machine;
  beginTime: string;
  endTime: string;
}
type problemsMap = Map<category,Map<number,[problemDetail]>>

function main(){
  addShiftIdentity()
  addProblemsList()
  addFlowsList()
  addTemperaturesList()
  addNotesList()
  addEmployeesList()
}
main()

async function addShiftIdentity(){
  try{
    const shiftId :ShiftId = await fetch("/fetch/report/current/shift/id")
      .then(res => res.json())
    document.getElementById("shift-id-section")!.innerHTML =`
        <p class="short-important-p">
          Shift: ${shiftId.shift}
          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
          Date: ${shiftId.date}
        </p>
    `
  } catch(err){
    console.log(err)
  }
}

function problemDetailsTable(category:string,mn :string,pds : [problemDetail]){
  return `
    <table>
      <thead>
        <tr>
          <th class="mini-button">-</th>
          <th>begin at</th>
          <th>ended at</th>
          <th>problems</th>
        </tr>
      </thead>
      <tbody id="${category}-${mn}-problems-list">
        ${(function(){
          let trs = ""
          if(pds){
            for (const pd of pds){
              trs += problemDetailModule(pd)
              }
          }
          return trs;
        })()}
      </tbody>
    </table>
  `
}

function categoryModule(category: category, mnpd : Map<string,[problemDetail]>){
  return `
    <div class="ghost top-bottom-border" id="${category.name}-category-id">
      <ul>
        ${(function(){
          let li = ""
          for(const [mn,pds] of mnpd){
            li += `
              <li>
                <div>
                ${(function(){
                  if(category.hasMachines){
                    return `
                      <button class="main-button"
                        onclick="toggle('${category.name}-${mn}-show')"
                        >${category.name} ${mn}</button>
                    `
                  }
                  return ''
                })()}
                  <div class="top-bottom-border ${category.hasMachines?'ghost':''}" id="${category.name}-${mn}-show">
                    <div id="${category.name}-${mn}-btns-container">
                      <button class="mini-button"
                        onclick="replaceForm('${category.name}','${mn}','${category.name}-${mn}-show')"
                      >+</button>
                      <button
                        onclick="deleteCategoryProblems('${category.name}','${mn}','${category.name}-${mn}-problems-list')"
                        class="mini-button"
                      >-</button>
                    </div>
                    ${problemDetailsTable(category.name,mn,pds)}
                  </div>
                </div>
              </li>
            `
          }
          return li
        })()}
      </ul>
    </div>
  `
}

function problemsListModule(problems : problemsMap){
  return `
    <ul>
      ${(function(){
        let lis = ""
        //mnpdm => machine number to list of problem details map
        for(const [category,mnpdm] of problems){
          const mnpd  = new Map(Object.entries(mnpdm)) as Map<string,[problemDetail]>
          if(category.name){
            lis += `
              <li>
                <div >
                  <button onclick="toggle('${category.name}-category-id')" class="issub">
                    ${category.name} PROBLEMS
                  </button>
                  ${categoryModule(category,mnpd)}
                </div>
              </li>
            `
          }
        }
        return lis
      })()}
    </ul>
    `

}
async function addProblemsList(){
  try{
    const problems :problemsMap = await (async function() {
      try{
        const result = new Map()
        const stringMap = new Map(Object
        .entries(await fetch("/fetch/report/categories/numbers/problems/mapping")
        .then(res => res.json())))
        stringMap.forEach((v,k) => result.set(JSON.parse(k),v))
        return result
      } catch(err){
        console.log(err)
        return new Map()
      }
    })()
    document.getElementById("problems-section")!.innerHTML += problemsListModule(problems)
  } catch(err){
    console.log(err)
  }
}

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

function problemsTitlesModule(pdId :string, problems : problem[]){
  return `
    <ol type="1" id="${pdId}-problems-list">
      ${(function(){
        let result =""
        problems.forEach(p =>{
          result += `
            <li id="${pdId}-${p.title}">
              <button
              onclick="deleteProblemTitle('${pdId}','${p.title}','${pdId}-${p.title}')"
              style="display:inline-block;">-</button>
              <span style="display:inline-block;">${p.title}</span>
            </li>
          `
        })
        return result
      })()}
      <li>
        <button
          onclick="listProblems('${pdId}')"
          class="mini-button"
        >+</button>
      </li>
    </ol>
  `
}

function problemDetailModule(pd : problemDetail){
  return `
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
      ${problemsTitlesModule(pd.id,pd.problems)}
      </td>
    </tr>
  `
}

function ProblemsListNewMember(machine: string,number: string, pd :problemDetail){
  document.getElementById(`${machine}-${number}-problems-list`)!
    .innerHTML += problemDetailModule(pd)
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

async function problemsListForm(uuid : string){
  return `
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

async function listProblems(uuid : string){
  const target = document.getElementById(uuid+"-problems-list")
  nativeContentMap.set(uuid+"-problems-list",target!.innerHTML)
  target!.innerHTML = await problemsListForm(uuid)
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
    ProblemsListNewMember(machine,number,pd)
  } catch(err){
    console.log(err)
  }
}

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

function flowMachineModule(flowId:string,machine: machine){
  return `
    <li id="${machine.id}-machine">
      <button
        onclick="removeFlowMachine('${flowId}','${machine.id}')"
        class="mini-button"
      >-</button>${machine.category.name}${machine.number?'-'+machine.number:''}
    </li>
  `
}

async function addFlowMachines(flowId : string){
  try{
    const machines = document.getElementById(flowId+"-machines-options") as HTMLSelectElement
    const formData = new FormData()
    formData.append('id',flowId)
    formData.append('machines', (function(){
        const chosenMachines: string[] = []
        for(let i = 0; i < machines.options.length; i++){
          if(machines.options[i].selected){
            chosenMachines.push(machines.options[i].value)
          }
        }
        return chosenMachines.toString()
    })())

    const result : [machine] = await fetch("/fetch/report/add/flow/machines",
      {method: 'POST', body: formData}).then(res => res.json())

    restoreContent(flowId+"-machines")
    const target = document.getElementById(flowId+"-machines")
    result.forEach(machine => {
      target!.innerHTML += flowMachineModule(flowId,machine)
    })
  } catch (err){
    console.log(err)
  }
}

function flowMemberModule(flow: flow){
 return `
    <tr id="${flow.id}-record">
      <td>
        {
        <button
            id="${flow.id}-btn"
            onclick="removeFlow('${flow.id}')"
            type="submit"
            class="mini-button"
            style="width: 100%"
        >-</button>
        }
      </td>
      <td>
        <ul id="${flow.id}-machines">
          ${(function(){
            let result = ""
            flow.suspendedMachines.forEach(m =>{
              result += flowMachineModule(flow.id,m)
            })
            return result
          })()}
          <li><button
            onclick="listMachines('${flow.id}')"
            class="mini-button"
          >+</button></li>
        </ul>
      </td>
      <td>${flow.maxFlow}</td>
      <td>${flow.minFlow}</td>
      <td>${flow.caseBeginTime}</td>
      <td id="${flow.id}-end" name="flow-end-time">${flow.caseEndTime}</td>
    </tr>
  `
}

function flowListNewMember(flow: flow){
  document.getElementById("flow-records-list")!.innerHTML += flowMemberModule(flow)
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


function flowListModule(flows : flow[]){
  return `
        <table>
          <thead>
            <tr>
              <th>-</th>
              <th>Suspended machines</th>
              <th>Max total flow</th>
              <th>Min total flow</th>
              <th>Start time</th>
              <th>End time</th>
            </tr>
          </thead>
          <tbody id="flow-records-list">
          ${(function(){
            let lis = ""
            for(const flow of flows){
                  lis += flowMemberModule(flow)
            }
          return lis
          })()}
          </tbody>
        </table>
    `
}
async function addFlowsList(){
  try{
    const flows :flow[] = await fetch("/fetch/report/current/flow")
              .then(res => res.json())
    document.getElementById("flow-section")!.innerHTML += flowListModule(flows)
    addDeleteFlowRecord()
  } catch(err){
    console.log(err)
  }
}

async function getMachinesOptions(){
  try{
    const jsonMap : Map<category,number[]> = await categoryMap
    let opts = ""
    jsonMap.forEach((numbers,category) =>{
      numbers.forEach(number =>{
        opts += `<option value="${category.name}-${number}">${category.name}${number?'-'+number:''}</option>`
      })
    })
    return opts;
  } catch (err){
    console.log(err)
    return ""
  }
}

async function flowFormModule(){
  try{
    const minTime = await flowMinTime();
    const maxTime = await shiftEnd();
    return `
          <div class="box-container">
            <div class="form-container">
              <h1> Total Flow record</h1>
              <button onclick="restoreContent('flow-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
              <form onsubmit="saveFlowRequest(); return false;" method="post">
              <label>suspended machines</label>
              <select multiple="multiple" name="machines" id="flow-machines-id" required>
                ${await getMachinesOptions()}
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
    return ""
  }
}

async function replaceFlowForm(){
  try{
    const target  = document.getElementById("flow-section")
    nativeContentMap.set("flow-section",target!.innerHTML)
    target!.innerHTML = await flowFormModule()
  } catch(err){
    console.log(err)
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

async function listMachines(uuid : string){
  try{
    const target = document.getElementById(uuid+"-machines")
    nativeContentMap.set(uuid+"-machines",target!.innerHTML)
    target!.innerHTML = `
        <div class="box-container">
          <div class="form-container">
            <form onsubmit="addFlowMachines('${uuid}'); return false;">
            <select multiple="multiple" id="${uuid}-machines-options" required>
              ${await getMachinesOptions()}
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

async function removeAllNotes(){
  try{
    const result = await fetch("/fetch/report/remove/all/note")
    if(result){
      document.getElementById("notes-records-list")!.innerHTML = ""
    }
  } catch(err){
    console.log(err)
  }
}
async function removeNote(note : string){
  try{
    const formData = new FormData()
    formData.append("note",note)
    const result = await fetch("/fetch/report/remove/note",
    {method:"POST",body: formData}).then(res => res.json())
    if(result){
      document.getElementById(`${note}-note-record`)!.remove()
    }
  } catch(err){
    console.log(err)
  }
}
async function saveNoteRequest(){
  try{
    const formData = new FormData()
    const notEl = document.getElementById("note-text") as HTMLTextAreaElement
    const note = notEl.value.split("\n").join("")
    formData.append("note",note)
    const result = await fetch("/fetch/report/add/note",
    {method:"POST",body: formData}).then(res => res.json())
    restoreContent('note-section')
    if(result){
      document.getElementById("notes-records-list")!.innerHTML += `
        <li id="${note}-note-record">
          <button
            onclick="removeNote('${note}')"
            class="mini-button"
            style="display:inline-block;width:7%; margin:2%;"
          >-</button>
          <p
            class="short-important-p"
            style="display:inline-block; width:77%; margin:2%;">${note}</p>
        </li>
      `
    }
  } catch(err){
    console.log(err)
  }
}

const noteFormModule = `
      <div class="box-container">
        <div class="form-container">
          <button onclick="restoreContent('note-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
          <form onsubmit="saveNoteRequest(); return false;">
          <label>NOTE</label>
          <textarea id="note-text" rows="4" cols="50" class="short-important-p"
                style="width:80%; margin:5%; color:white;">...</textarea>
          <button type="submit">Submit</button>
          </form>
        </div>
      </div>
`


function replaceNoteForm(){
  const target = document.getElementById('note-section')
  nativeContentMap.set("note-section",target!.innerHTML)
  target!.innerHTML = noteFormModule
}

function noteModule(note : note){
  return `
    <li id="${note.note}-note-record">
      <button
        onclick="removeNote('${note.note}')"
        class="mini-button"
        style="display:inline-block;width:7%; margin:2%;"
      >-</button>
      <p
        class="short-important-p"
        style="display:inline-block; width:77%; margin:2%;"
      >${note.note}</p>
  </li>
  `
}

function noteListModule(notes: note[]){
  return `
          <ul id="notes-records-list">
          ${(function(){
            let lis = ""
            for(const note of notes){
              lis += noteModule(note)
            }
          return lis
          })()}
				</ul>
    `
}
async function addNotesList(){
  try{
    const notes :note[] = await fetch("/fetch/report/current/notes")
              .then(res => res.json())
    document.getElementById("note-section")!.innerHTML += noteListModule(notes)
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

function addTempMember(temp :temp){
  const ids : string[] = []
  const records = document.getElementById("temps-records-list")
  for(const i of records!.getElementsByTagName("li")){
    ids.push(i.getAttribute("id")!)
  }
  if(ids.includes(temp.machine.id)){
    document.getElementById(temp.machine.id)!.remove()
  }
  records!.innerHTML += temperatureModule(temp)
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

async function tempFormModule(){
  try{
    const jsonMap: Map<category,number[]> = await categoryMap
    return `
          <div class="box-container">
            <div class="form-container">
              <h1> Temperature record</h1>
              <button onclick="restoreContent('temp-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
              <form onsubmit="saveTempRequest(); return false;">
              <label name="machine" id="machine">target machine</label>
              <select id="temp-machine-id" required>
                ${
                  (function(){
                    let opts = ""
                    jsonMap.forEach((numbers,category) =>{
                      if(category.hasTemperature){
                        numbers.forEach(number =>{
                          opts += `<option value="${category.name}-${number}">${category.name}${number?'-'+number:''}</option>`
                        })
                      }
                    })
                    return opts;
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
    return ""
  }
}

async function replaceTempForm(){
  try{
    const target = document.getElementById("temp-section")
    nativeContentMap.set("temp-section",target!.innerHTML)
    target!.innerHTML = await tempFormModule()
  } catch(err){
    console.log(err)
  }
}

function tempListModule(temps:[temp]){
  return `
          <ul id="temps-records-list">
          ${(function(){
            let lis = ""
            for(const temp of temps){
              lis += temperatureModule(temp)
            }
          return lis
          })()}
				</ul>
    `

}
async function addTemperaturesList(){
  try{
    const temps :[temp] = await fetch("/fetch/report/current/temps")
              .then(res => res.json())
    document.getElementById("temp-section")!.innerHTML += tempListModule(temps)
  } catch(err){
    console.log(err)
  }
}

function temperatureModule(temp :temp){
  return `
    <li id="${temp.machine.id}">
      <button
        onclick="removeTemp('${temp.machine.id}')"
        class="mini-button"
        style="display:inline-block;width:7%; margin:2%;"
      >-</button>
      <p
        class="short-important-p"
        style="display:inline-block; width:77%; margin:2%;">
        MACHINE : ${temp.machine.category.name}${temp.machine.number?'-'+temp.machine.number:''}
        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        MAXIMUM : ${temp.max}
        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        MINIMUM : ${temp.min}
      </p>
    </li>
  `
}

async function removeAllEmps(){
  try{
    const result = await fetch("/fetch/report/remove/all/emp")
      .then(res => res.json())
    if(result){
      document.getElementById("emps-records-list")!.innerHTML = ""
    }
  } catch(err){
    console.log(err)
  }
}
async function removeEmp(id: string) {
  try{
    const formData = new FormData()
    formData.append("id",id)
    const result = await fetch("/fetch/report/remove/emp",
      {method: 'POST',body: formData}).then(res => res.json())

    if(result){
      document.getElementById(`${id}-emp`)!.remove()
    }
  } catch(err){
    console.log(err)
  }
}
async function addEmployee(){
  try{
    const opts = document.getElementById("employees-options") as HTMLSelectElement
    const formData = new FormData()
    formData.append("emp",opts.value)
    const emp = await fetch("/fetch/report/add/emp"
      ,{method: 'POST',body: formData}).then(res => res.json())

    restoreContent('employee-section')
    if(emp){
      document.getElementById("emps-records-list")!.innerHTML += employeeModule(emp)
    }
  } catch(err){
    console.log(err)
  }
}

function employeeModule(emp: empName){
  return `
  <li id="${emp.id}-emp">
    <button style="display:inline-block; width:7%; margin:2%;"
        onclick="removeEmp('${emp.id}')"
        class="mini-button"
    >-</button>
    <p style="display:inline-block; width:77%; margin:2%;"
        class="short-important-p">${emp.fullName}</p>
  </li>
  `
}

function employeeFormModule(usernames: string[]){
  return `
        <div class="box-container">
          <div class="form-container">
            <button onclick="restoreContent('employee-section')" style="width:2%; display:block; padding: 0px; color:red;">X</button>
            <form onsubmit="addEmployee(); return false;">
            <select name="emp" id="employees-options" required>
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
}

async function listEmployees(){
  try{
    const usernames : string[] = await names
    const target = document.getElementById("employee-section")
    nativeContentMap.set("employee-section",target!.innerHTML)
    target!.innerHTML = employeeFormModule(usernames)
  } catch (err){
    console.log(err)
  }
}

function employeeListModule(empsNames: [empName]){
  return `
				<ul id="emps-records-list">
          ${(function(){
            let lis  = ""
            for(const emp of empsNames){
              lis += employeeModule(emp)
            }
          return lis
          })()}
				</ul>
    `
}
async function addEmployeesList(){
  try{
    const empsNames :[empName] = await fetch("/fetch/report/current/emps/names")
      .then(res => res.json())
    document.getElementById("employee-section")!
      .innerHTML += employeeListModule(empsNames)
  } catch(err){
    console.log(err)
  }
}
