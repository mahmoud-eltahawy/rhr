function defineProblem(){
    document.getElementById("action-place")!.innerHTML =`
    <div>
		<h1>Define Problem</h1>
		<form onsubmit="saveProblem(); return false;">
            <label style="display:block;" for="name-id">Name</label>
			<input style="display:block;" type="text" id="name-id" required>
            <label style="display:block;" for="desc-id">Description</label>
            <textarea id="desc-id" rows="4"
                    cols="50" class="short-important-p"
                    style="width:95%; margin:5%; color:white; display:block;"></textarea>
			<button type="submit">Submit</button>
		</form>
	</div>
    `
}
async function saveProblem() {
    const name = document.getElementById("name-id") as HTMLInputElement   
    const desc = document.getElementById("desc-id") as HTMLTextAreaElement
    const formDate = new FormData()
    formDate.append("name",name.value)
    formDate.append("desc",desc.value)
    let result : boolean = await fetch("/define/problem",
    {method: 'POST',body: formDate}).then(res => res.json())
    if(result){
        alert(`${name.value} defined successfully`)
    } else {
        alert("failed to store the problem")
    }
}
async function defineMachine(){
    const categories : string[] = await fetch("/define/get/all/categories")
        .then(res => res.json())
    document.getElementById("action-place")!.innerHTML =`
    <div>
		<h1>Add Machine</h1>
		<form onsubmit="saveMachine(); return false;">
            <label style="display:block;" for="category">Category Name</label>
            <select required="required" id="category-id">
            ${(() => {
                let opts = ""
                categories.forEach(c => {
                    opts += `<option value="${c}">${c}</option>`
                });
                return opts
            })()}
            </select>
			<button type="submit">Submit </button>
		</form>
	</div>
    `
}
async function saveMachine() {
    const category = document.getElementById("category-id") as HTMLSelectElement   
    const formDate = new FormData()
    formDate.append("category",category.value)
    let machineNumber : number = await fetch("/define/machine",
    {method: 'POST',body: formDate}).then(res => res.json())
    if(machineNumber){
        alert(`${category.value} ${machineNumber} defined successfully`)
    } else {
        alert("failed to store the problem")
    }
}
function defineCategory(){
    document.getElementById("action-place")!.innerHTML =`
    <div>
		<h1>Define Category</h1>
		<form onsubmit="saveCategory(); return false;" method="post">
            <label style="display:block;" for="name">Category Name</label>
            <input style="display:block;" type="text" id="name-id" required="required">
            <label style="display:block;" for="hasMachines">Has Machines</label>
            <select required="required" id="hasMachines-id">
                <option value="true" selected>YES</option>
                <option value="false">NO</option>
            </select>
            <label style="display:block;" for="hasTemperature">Has Temperature</label>
            <select required="required" id="hasTemperature-id">
                <option value="true" selected>YES</option>
                <option value="false">NO</option>
            </select>
            <button type="submit">Submit </button>
		</form>
	</div>
    `
}
async function saveCategory() {
    const name = document.getElementById("name-id") as HTMLInputElement   
    const hasMachines = document.getElementById("hasMachines-id") as HTMLSelectElement
    const hasTemperature = document.getElementById("hasTemperature-id") as HTMLSelectElement
    const formDate = new FormData()
    formDate.append("name",name.value)
    formDate.append("hasMachines",hasMachines.value)
    formDate.append("hasTemperature",hasTemperature.value)
    let result : boolean = await fetch("/define/category",
    {method: 'POST',body: formDate}).then(res => res.json())
    if(result){
        alert(`${name.value} defined successfully`)
    } else {
        alert("failed to store the category")
    }
}