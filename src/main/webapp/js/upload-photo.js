async function uploadFile() {
    let formData = new FormData();
    formData.append("file", ajaxfile.files[0]);
    const response = await fetch('upload', {
        method: "POST",
        body: formData
    });
    const {success} = await response.json();
    console.log(success)
    if (!success) {
        alert("Error Upload!")
    }
    // await sessionStorage.removeItem('photo')
    setTimeout(function () {
        location.reload();
    },1000);
}

const input = document.getElementById("ajaxfile");

function openForm() {
    input.click();
}

const btnUpload = document.querySelector(".btnUpload")
btnUpload.addEventListener('click', openForm)