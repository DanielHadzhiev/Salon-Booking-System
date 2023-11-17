let isEmailValid = true;
let isPasswordValid = true;

vlaidateEmail();
validatePassword();
returnForm();

function vlaidateEmail(){

  let emailInput = document.querySelector('.email-bar');
let emialMessage = document.querySelector('.email-message');
const emailRegex = /[A-Za-z0-9]+@[A-Za-z0-9]+/i;

document.querySelector('.submit-button').addEventListener('click',()=>{

if(emailInput.value.trim()===""){
  emialMessage.style.display = 'block';
  emialMessage.textContent = 'Please enter email.';
  emailInput.style.backgroundColor = '#FF3C38';
  isEmailValid = false;
}
else if(!emailRegex.test(emailInput.value)){
  emialMessage.style.display = 'block';
  emialMessage.textContent = 'Email should contain @ in the middle.';
  emailInput.style.backgroundColor = '#FF3C38';
  isEmailValid = false;
}
else{
  emialMessage.style.display = 'none';
  emailInput.style.backgroundColor = 'white';
  isEmailValid = true;
}

});
}

function validatePassword(){

  let passwordInput = document.querySelector('.password-bar');
  let passwordMessage = document.querySelector('.password-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(passwordInput.value.trim()===""){
    passwordMessage.style.display = 'block';
    passwordMessage.textContent = 'Please enter password.';
    passwordInput.style.backgroundColor = '#FF3C38';
    isPasswordValid = false;
  }
  else{
    passwordMessage.style.display = 'none';
    passwordInput.style.backgroundColor = 'white';
    isPasswordValid = true;
  }
  
  });
}
function returnForm(){
  document.addEventListener('DOMContentLoaded', ()=> {
    const form = document.querySelector('.log-in-form');
  
    form.addEventListener('submit', function(event) {
      if (!isEmailValid || !isPasswordValid) {
        event.preventDefault();
      }
    });
  });
  }