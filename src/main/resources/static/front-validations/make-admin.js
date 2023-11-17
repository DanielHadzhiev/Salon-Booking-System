let isMakeAdminValid = true;

validateAdmin();
returnForm();


function validateAdmin(){

  let genderInput = document.querySelector('.user-select');
  let genderMessage = document.querySelector('.make-admin-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(genderInput.value === ""){
    genderMessage.style.visibility = 'visible';
    genderMessage.textContent = 'Please select user.';
    genderInput.style.backgroundColor = '#FF3C38';
    isMakeAdminValid=false;
  }
  else{
    genderMessage.style.visibility = 'hidden';
    genderInput.style.backgroundColor = 'white';
    isMakeAdminValid=true;
  }
  
  });
}

function returnForm(){
  document.addEventListener('DOMContentLoaded', ()=> {
    const form = document.querySelector('.make-admin-form');
  
    form.addEventListener('submit', function(event) {
      if (!isMakeAdminValid) {
        event.preventDefault();
      }
    });
  });
  }