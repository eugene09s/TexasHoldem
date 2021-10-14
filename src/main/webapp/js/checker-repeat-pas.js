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

const currentPass = document.getElementById('currentPass');
function showPass() {
    if (currentPass.type === "password") {
        currentPass.type = "text";
    } else {
        currentPass.type = "password";
    }
}