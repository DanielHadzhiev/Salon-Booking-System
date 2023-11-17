let isCommentsValid = true;
let isServiceValid = true;
let isDateValid = true;

validateComments();
validateDate();
validateServices();
returnForm();

function validateComments(){

  let commentsInput = document.querySelector('.comments-section');
  let commentsMessage = document.querySelector('.comments-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(commentsInput.value.trim()===""){
    commentsMessage.style.display = 'block';
    commentsMessage.textContent = 'Please enter comment.';
    commentsInput.style.backgroundColor = '#FF3C38';
    isCommentsValid = false;
  }
  else{
    commentsMessage.style.display = 'none';
    commentsInput.style.backgroundColor = 'white';
    isCommentsValid=true;
  }
  
  });

}

function validateDate(){

  let dateInput = document.querySelector('.date-time-select');
  let dateMessage = document.querySelector('.date-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(dateInput.value.trim()===""){
    dateMessage.style.display = 'block';
    dateMessage.textContent = 'Please enter date.';
    dateInput.style.backgroundColor = '#FF3C38';
    isDateValid = false;
  }
  else{
    dateMessage.style.display = 'none';
    dateInput.style.backgroundColor = 'white';
    isDateValid = true;
  }
  
  });

}
function validateServices(){

  let servicesInput = document.querySelector('.service-select');
  let servicesMessage = document.querySelector('.service-message');
  
  document.querySelector('.submit-button').addEventListener('click',()=>{
  
  if(servicesInput.value.trim()===""){
    servicesMessage.style.display = 'block';
    servicesMessage.textContent = 'Please select a service.';
    servicesInput.style.backgroundColor = '#FF3C38';
    isServiceValid = false;
  }
  else{
    servicesMessage.style.display = 'none';
    servicesInput.style.backgroundColor = 'white';
    isServiceValid = true;
  }
  
  });

}
function returnForm(){
  document.addEventListener('DOMContentLoaded', ()=> {
    const form = document.querySelector('.reservation-form');
  
    form.addEventListener('submit', function(event) {
      if (!isCommentsValid|| !isDateValid || !isServiceValid) {
        event.preventDefault();
      }
    });
  });
  }