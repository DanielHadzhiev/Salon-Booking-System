
let isEmailValid = true;

vlaidateEmail();
returnForm();

function vlaidateEmail(){

  let emailInput = document.querySelector('.name-input');
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
function returnForm(){
  document.addEventListener('DOMContentLoaded', ()=> {
    const form = document.querySelector('.name-form');
  
    form.addEventListener('submit', function(event) {
      if (!isEmailValid) {
        event.preventDefault();
      }
    });
  });
  }