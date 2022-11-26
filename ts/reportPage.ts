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
      <tr>
        <td>
          <form action="/report/remove/problem/?id=${pd.id}" method="post">
            <button
                type="submit"
                class="mini-button"
                style="width: 100%">
              -
            </button>
          </form>
        </td>
        <td>${pd.beginTime}</td>
        <td>${pd.endTime}</td>
        <td id="${pd.id}-problems">
          <ol type="1">
            ${(function() : string{
              let result =""
              pd.problems.forEach(p =>{
                result += `
                  <li>
                    <a style="display:inline-block;"
                        href="/report/remove/problem/problem/?id=${pd.id}&title=${p.title}"
                        method="post">
                      <button>-</button>
                    </a>
                    <span style="display:inline-block;">${p.title}</span>
                  </li>
                `
              })
              return result
            })()}
            <li>
              <button onclick="listProblems('${pd.id}')" class="mini-button">+</button>
            </li>
          </ol>
        </td>
      </tr>
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
    const pd :{
      id: string;
      shiftId:string;
      problems:[{
        title: string;
        descripion: string;
      }];
      machine:{
       id: string;
       category: string;
       number: number; 
      };
      beginTime: string;
      endTime: string;
    } = await fetch("/fetch/report/add/machine/problem"
    ,{method:'POST',body: formData})
    .then(res => res.json());
    console.log(`
      the id     :  ${pd.id}
      shift id   :  ${pd.shiftId}
      begin time :  ${pd.beginTime}
      end time   :  ${pd.endTime}
      problems   :  ${pd.problems.map(p=> `${p.title} | ${p.descripion}`).toString()}
      machine    :  ${`${pd.machine.id} | ${pd.machine.category} | ${pd.machine.number}`}
    `)
    restoreContent(fieldId)
    elementListNewMember(machine,number,pd)
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
    for (const [category,numbers] of jsonMap) {
      mList.set(`${category}-btns-container`,
        {vsize: numbers.length, catnum:{cat: category,num: 0}})
    }
    return mList
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

async function listProblems(uuid : string){
  document.getElementById(uuid+"-problems")!.innerHTML = `
      <div class="box-container">
        <div class="form-container">
          <form action="/report/add/problem/problems" method="post">
          <input type="hidden" id="id" name="id" value="${uuid}">
          <select multiple="multiple" name="titles" id="titles" required>
            ${await problemsOptions()}
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
    const minTime = await flowMinTime();
    const maxTime = await shiftEnd();   
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
  if(arr.length > 0){
    document.getElementById(arr[arr.length - 1].id.slice(0,-4) +'-btn')!.style.display = 'block'
  }
}

addMessage()
addDeleteFlowRecord()
// addAllPlusButtons()