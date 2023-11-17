let isNameValid = true;

validateNews();
returnForm();
function validateNews(){

  let nameInput = document.querySelector('.comments-section');
  let nameMessage = document.querySelector('.news-message');
  
  document.querySelector('.post-button').addEventListener('click',()=>{
  
  if(nameInput.value.trim()===""){
    nameMessage.style.display = 'block';
    nameMessage.textContent = 'Please enter content.';
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
      const form = document.querySelector('.post-form');
    
      form.addEventListener('submit', function(event) {
        if (!isNameValid) {
          event.preventDefault();
        }
      });
    });
    }