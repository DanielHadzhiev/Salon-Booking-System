let service = document.querySelector('.service-select');
let additional = document.querySelector('.washing-checkbox-div');


service.addEventListener('change',()=>{

    const selectedOption = service.options[service.selectedIndex];

    const selectedText = selectedOption.text;

if(selectedText.includes('Trimming')){
 additional.style.opacity = 1;
}
else if(selectedText.includes( 'Dyeing')){
  additional.style.opacity = 1;
}
else if(selectedText.includes('Pensioners')){
  additional.style.opacity = 1;
}
else if(selectedText.includes('Curls')){
  additional.style.opacity = 1;
}
else{
  additional.style.opacity = 0;
}

})