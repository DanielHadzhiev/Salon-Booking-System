
let isNameValid = true;

validateName();
returnForm();
function validateName(){

  let nameInput = document.querySelector('.name-input');
  let nameMessage = document.querySelector('.name-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(nameInput.value.trim()===""){
    nameMessage.style.display = 'block';
    nameMessage.textContent = 'Please enter name.';
    nameInput.style.backgroundColor = '#FF3C38';
    isNameValid = false;
  }
  else{
    nameMessage.style.display = 'none';
    nameInput.style.backgroundColor = 'white';
    isNameValid = true;
  }
  
  });
  }
 function returnForm(){
    document.addEventListener('DOMContentLoaded', ()=> {
      const form = document.querySelector('.name-form');
    
      form.addEventListener('submit', function(event) {
        if (!isNameValid) {
          event.preventDefault();
        }
      });
    });
    } 