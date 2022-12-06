const shiftIdId = (function(){
  const formDate = new FormData()
  const id = localStorage.getItem("shiftIdId")
  if(id){
    formDate.append("id",id)
  }
  localStorage.removeItem("shiftIdId")
  return formDate
})()
class Problems{
  public async add(){
    try{
      const problems = await this.#parse()
      if(problems){
        document.getElementById("problems-section")!.innerHTML += this.#module(problems)
      }
    } catch(err){
      console.log(err)
    }
  }

  async #fetch():Promise<Map<string,string>| undefined> {
    try{
      return new Map(Object.entries
          (await fetch("/fetch/shift/categories/numbers/problems/mapping"
          ,{method: 'POST', body:shiftIdId}).then(res => res.json())))
    } catch(err){
      console.log(err)
    }
  }

   async #parse() {
    try{
      const result : problemsMap = new Map()
      const stringMap = await this.#fetch()
      if(stringMap){
        stringMap.forEach((v,k) => result.set(JSON.parse(k),JSON.parse(v)))
      }
      return result
    } catch(err){
      console.log(err)
    }
  }
  #module(problems : problemsMap){
    return `
      <ul>
        ${(() =>{
          let lis = ""
          //mnpdm => machine number to list of problem details map
          for(const [category,mnpdm] of problems){
            const mnpd  = new Map(Object.entries(mnpdm))
            lis += this.#header(category,mnpd)
          }
          return lis
        })()}
      </ul>
      `
  }
  #header(category: category, content :Map<string,[problemDetail]>) :string{
    return `
      <li>
        <div>
          <button onclick="toggles('${category.name}-category-id')" class="issub">
            ${category.name} PROBLEMS
          </button>
          ${this.#category(category,content)}
        </div>
      </li>
    `
  }
  #category(category: category, mnpd : Map<string,[problemDetail]>){
    return `
      <div class="ghost top-bottom-border" id="${category.name}-category-id">
        <ul>
          ${(()=>{
            let li = ""
            for(const [mn,pds] of mnpd){
              li += `
                <li>
                  <div>
                    ${this.#headerButton(category,mn)}
                    <div class="top-bottom-border ${category.hasMachines?'ghost':''}" id="${category.name}-${mn}-show">
                      ${this.#table(category.name,mn,pds)}
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
  #headerButton(category: category, number: string){
    if(category.hasMachines){
      return `
        <button class="main-button"
          onclick="toggles('${category.name}-${number}-show')"
          >${category.name} ${number}</button>
      `
    }
    return ''
  }
  #table(category:string,mn :string,pds : [problemDetail]){
    return `
      <table>
        <thead>
          <tr>
            <th>begin at</th>
            <th>ended at</th>
            <th>problems</th>
          </tr>
        </thead>
        <tbody id="${category}-${mn}-problems-list">
          ${(() => {
            let trs = ""
            if(pds){
              for (const pd of pds){
                trs += this.#detail(pd)
                }
            }
            return trs;
          })()}
        </tbody>
      </table>
    `
  }
  #detail(pd : problemDetail){
    return `
      <tr id="${pd.id}-member">
        <td>${pd.beginTime}</td>
        <td>${pd.endTime}</td>
        <td id="${pd.id}-problems">
        ${this.#titles(pd.id,pd.problems)}
        </td>
      </tr>
    `
  }
  #titles(pdId :string, problems : problem[]){
  return `
    <ol type="1" id="${pdId}-problems-list">
      ${(function(){
        let result =""
        problems.forEach(p =>{
          result += `
            <li id="${pdId}-${p.title}">
              <span style="display:inline-block;">${p.title}</span>
            </li>
          `
        })
        return result
      })()}
    </ol>
  `
}

}

class Flows {
  public async add(){
    try{
      const flows :flow[] = await fetch("/fetch/shift/flow"
          ,{method: 'POST', body:shiftIdId}).then(res => res.json())
      document.getElementById("flow-section")!.innerHTML += this.#module(flows)
    } catch(err){
      console.log(err)
    }
  }

  #module(flows : flow[]){
    return `
          <table>
            <thead>
              <tr>
                <th>Suspended machines</th>
                <th>Max total flow</th>
                <th>Min total flow</th>
                <th>Start time</th>
                <th>End time</th>
              </tr>
            </thead>
            <tbody id="flow-records-list">
            ${(() =>{
              let lis = ""
              for(const flow of flows){
                    lis += this.#member(flow)
              }
            return lis
            })()}
            </tbody>
          </table>
      `
  }
  #member(flow: flow){
    return `
        <tr id="${flow.id}-record">
          <td>
            <ul id="${flow.id}-machines">
              ${(()=>{
                let result = ""
                flow.suspendedMachines.forEach(m =>{
                  result += `
                    <li id="${m.id}-machine">
                      ${m.category.name}${m.number?'-'+m.number:''}
                    </li>
                  `
                })
                return result
              })()}
            </ul>
          </td>
          <td>${flow.maxFlow}</td>
          <td>${flow.minFlow}</td>
          <td>${flow.caseBeginTime}</td>
          <td>${flow.caseEndTime}</td>
        </tr>
      `
  }
}

class Temps{
  public async add(){
    try{
      const temps :[temp] = await fetch("/fetch/shift/temps",
      {method: 'POST', body: shiftIdId}).then(res => res.json())
      document.getElementById("temp-section")!.innerHTML += this.#module(temps)
    } catch(err){
      console.log(err)
    }
  }

  #module(temps:[temp]){
    return `
            <ul id="temps-records-list">
            ${(()=>{
              let lis = ""
              for(const temp of temps){
                lis += this.#member(temp)
              }
            return lis
            })()}
          </ul>
      `
  }
  #member(temp :temp){
    return `
      <li id="${temp.machine.id}">
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
}

class Notes{
  public async add(){
    try{
      const notes :note[] = await fetch("/fetch/shift/notes",
      {method: 'POST', body: shiftIdId}).then(res => res.json())
      document.getElementById("note-section")!.innerHTML += this.#module(notes)
    } catch(err){
      console.log(err)
    }
  }

  #module(notes: note[]){
    return "<ul id='notes-records-list'>"
            +(()=>{
              let lis = ""
              for(const note of notes){
                lis += this.#member(note)
              }
            return lis
            })()
          +"</ul>"
  }

  #member(note : note){
    return `
      <li id="${note.note}-note-record">
        <p
          class="short-important-p"
          style="display:inline-block; width:77%; margin:2%;"
        >${note.note}</p>
    </li>
    `
  }
}

class Emps{
  public async add(){
    try{
      const empsNames :[empName] = await fetch("/fetch/shift/emps/names",
      {method: 'POST', body: shiftIdId}).then(res => res.json())
      document.getElementById("employee-section")!
        .innerHTML += this.#module(empsNames)
    } catch(err){
      console.log(err)
    }
  }

  #module(empsNames: [empName]){
    return `
          <ul id="emps-records-list">
            ${(()=>{
              let lis  = ""
              for(const emp of empsNames){
                lis += this.#member(emp)
              }
            return lis
            })()}
          </ul>
      `
  }
  #member(emp: empName){
    return `
    <li id="${emp.id}-emp">
      <p style="display:inline-block; width:77%; margin:2%;"
          class="short-important-p">${emp.fullName}</p>
    </li>
    `
  }
}

class Shifts{
  public async add(){
    try{
      document.getElementById("shift-id-section")!.innerHTML = await this.#module()
    } catch(err){
      console.log(err)
    }
  }

  async #module(){
    try{
      const shiftId :ShiftId = await this.#fetch()
      return `
        <p class="short-important-p">
          Shift: ${shiftId.shift}
          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
          Date: ${shiftId.date}
        </p>
      `
    } catch(err){
      console.log(err)
      return ""
    }
  }

  async #fetch() {
    try{
      return fetch("/fetch/shift/id",
      {method: 'POST', body:shiftIdId}).then(res => res.json())
    } catch(err){
      console.log(err)
    }
  }
}

function mains(){
  new Shifts().add()
  new Problems().add()
  new Flows().add()
  new Temps().add()
  new Notes().add()
  new Emps().add()
}
mains()

function toggles(id :string) {
  const x = document.getElementById(id)
  if(x){
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }
}