async function exportEveryShift(){
  download("AAA.json",JSON.stringify(await fetchEveryShift()))
}
async function fetchEveryShift(){
  return fetch("/io/xall").then(res => res.json())
}


function download(filename : string, text : string) {
  const element = document.createElement('a');
  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
  element.setAttribute('download', filename);

  element.style.display = 'none';
  document.body.appendChild(element);

  element.click();

  document.body.removeChild(element);
}