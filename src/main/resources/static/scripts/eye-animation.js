let showPassword = document.querySelector('.show-password');
let password = document.querySelector('.password-bar');

let eyeOn = new Image();
eyeOn.src = '/images/eye.svg';

let eyeOff = new Image();
eyeOff.src = '/images/eye-off.svg';

showPassword.addEventListener('click',()=>{

if(password.value === ''){
  showPassword.disabled=true;
}
else{
  showPassword.disabled=false;
if(showPassword.src===eyeOff.src){
  password.setAttribute("type", "text");
  showPassword.src = eyeOn.src;
}
else{
  password.setAttribute("type", "password");
  showPassword.src = eyeOff.src;
}
}
})

