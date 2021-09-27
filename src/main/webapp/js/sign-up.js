const inputs = document.querySelectorAll(".password");
const passErrorEl = document.querySelector(".password-error");

//Check repeate password
function checkPassword(pass1, pass2) {
    if (pass1 !== pass2) {
        passErrorEl.classList.remove("d-none");
    } else {
        passErrorEl.classList.add("d-none");
    }
}
inputs[1].addEventListener("input", ()=> checkPassword(inputs[0].value, inputs[1].value));

//Check exist login
$(document).ready(function () {
    $('#loginRegForm').blur(function () {
        $.ajax({
            url : 'poker?command=checkExistLogin',
            data : {
                login : $('#loginRegForm').val()
            },
            success : function (response) {
                console.log("AJAXCheckLogin: "+ response)
                if (response === 'true') {
                    $('#loginRegForm').addClass("border-danger");
                    $('.login-error').removeClass("d-none");
                } else {
                    $('#loginRegForm').removeClass("border-danger");
                    $('.login-error').addClass("d-none");
                }
            }
        })
    })
})

//Check exist email
$(document).ready(function () {
    $('#emailRegForm').blur(function () {
        $.ajax({
            url : 'poker?command=checkExistEmail',
            data : {
                email : $('#emailRegForm').val()
            },
            success : function (response) {
                console.log("AJAXCheckEmail: "+ response)
                if (response === 'true') {
                    $('#emailRegForm').addClass("border-danger");
                    $('.email-error').removeClass("d-none");
                } else {
                    $('#emailRegForm').removeClass("border-danger");
                    $('.email-error').addClass("d-none");
                }
            }
        })
    })
})