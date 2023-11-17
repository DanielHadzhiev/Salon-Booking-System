let img = document.querySelector('.default-image');
let input = document.querySelector('.form-file');

input.onchange = (e) => {
  if(input.files[0]){
    img.src = URL.createObjectURL(input.files[0]);
  }
};