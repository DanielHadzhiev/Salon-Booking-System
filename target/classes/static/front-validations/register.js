let isNameValid = true;
let isImageValid = true;
let isEmailValid = true;
let isPasswordValid = true;
let isGenderValid = true;

validateImage();
validateName();
vlaidateEmail();
validatePassword();
validateGender();
returnForm();



function validateImage() {
  let fileInput = document.querySelector('.form-file');
  let validationMessage = document.querySelector('.image-message');
  let image = document.querySelector('.image');

  document.querySelector('.submit-button').addEventListener('click',()=>{
   
    if (fileInput.files.length === 0) {
      validationMessage.style.visibility = 'visible';
      validationMessage.textContent = 'Please select a picture to upload.';
      image.style.backgroundColor = '#FF3C38';
      isImageValid=false;
    }
    else{
      validationMessage.style.visibility = 'hidden';
      image.style.backgroundColor = 'white';
      isImageValid=true;
    }

     });
}

function validateName(){

let nameInput = document.querySelector('.name-bar');
let nameMessage = document.querySelector('.name-message');

document.querySelector('.submit-button').addEventListener('click',()=>{

if(nameInput.value.trim()===""){
  nameMessage.style.visibility = 'visible';
  nameMessage.textContent = 'Plese enter name.';
  nameInput.style.backgroundColor = '#FF3C38';
  isNameValid=false;
}
else{
  nameMessage.style.visibility = 'hidden';
  nameInput.style.backgroundColor = 'white';
  isNameValid = true;
}

});
}

function vlaidateEmail(){

  let emailInput = document.querySelector('.email-bar');
let emialMessage = document.querySelector('.email-message');
const emailRegex = /[A-Za-z0-9]+@[A-Za-z0-9]+/i;

document.querySelector('.submit-button').addEventListener('click',()=>{

if(emailInput.value.trim()===""){
  emialMessage.style.visibility = 'visible';
  emialMessage.textContent = 'Please enter email.';
  emailInput.style.backgroundColor = '#FF3C38';
  isEmailValid=false;
}
else if(!emailRegex.test(emailInput.value)){
  emialMessage.style.visibility = 'visible';
  emialMessage.textContent = 'Email should contain @ in the middle.';
  emailInput.style.backgroundColor = '#FF3C38';
  isEmailValid = false;
}
else{
  emialMessage.style.visibility = 'hidden';
  emailInput.style.backgroundColor = 'white';
  isEmailValid=true;
}

});
}

function validatePassword(){

  let passwordInput = document.querySelector('.password-bar');
  let passwordMessage = document.querySelector('.password-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(passwordInput.value.trim()===""){
    passwordMessage.style.visibility = 'visible';
    passwordMessage.textContent = 'Please enter password.';
    passwordInput.style.backgroundColor = '#FF3C38';
    isPasswordValid = false;
  }
  else{
    passwordMessage.style.visibility = 'hidden';
    passwordInput.style.backgroundColor = 'white';
    isPasswordValid=true;
  }
  
  });

}

function validateGender(){

  let genderInput = document.querySelector('.select-gender');
  let genderMessage = document.querySelector('.gender-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(genderInput.value === ""){
    genderMessage.style.visibility = 'visible';
    genderMessage.textContent = 'Please select gender.';
    genderInput.style.backgroundColor = '#FF3C38';
    isGenderValid=false;
  }
  else{
    genderMessage.style.visibility = 'hidden';
    genderInput.style.backgroundColor = 'white';
    isGenderValid=true;
  }
  
  });
}
function returnForm(){
document.addEventListener('DOMContentLoaded', ()=> {
  const form = document.querySelector('.registration-form');

  form.addEventListener('submit', function(event) {
    if (!isEmailValid || !isGenderValid || !isImageValid || !isNameValid || !isPasswordValid) {
      event.preventDefault();
    }
  });
});
}
