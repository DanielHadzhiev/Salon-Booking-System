
let isNewPasswordValid = true;
let isOldPasswordValid = true;

validateNewPassword();
validateOldPassword();
returnForm();

function validateNewPassword(){

  let passwordInput = document.querySelector('.password-bar');
  let passwordMessage = document.querySelector('.new-password-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(passwordInput.value.trim()===""){
    passwordMessage.style.display = 'block';
    passwordMessage.textContent = 'Please enter new password.';
    passwordInput.style.backgroundColor = '#FF3C38';
    isNewPasswordValid = false;
  }
  else{
    passwordMessage.style.display = 'none';
    passwordInput.style.backgroundColor = 'white';
    isNewPasswordValid=true;
  }
  
  });

}

function validateOldPassword(){

  let passwordInput = document.querySelector('.password-input');
  let passwordMessage = document.querySelector('.old-password-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(passwordInput.value.trim()===""){
    passwordMessage.style.display = 'block';
    passwordMessage.textContent = 'Plese enter old password.';
    passwordInput.style.backgroundColor = '#FF3C38';
    isOldPasswordValid = false;
  }
  else{
    passwordMessage.style.display = 'none';
    passwordInput.style.backgroundColor = 'white';
    isOldPasswordValid = true;
  }
  
  });

}
function returnForm(){
  document.addEventListener('DOMContentLoaded', ()=> {
    const form = document.querySelector('.password-form');
  
    form.addEventListener('submit', function(event) {
      if (!isNewPasswordValid || !isOldPasswordValid) {
        event.preventDefault();
      }
    });
  });
  }