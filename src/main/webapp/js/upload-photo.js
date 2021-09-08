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
    location.reload()
}

const input = document.getElementById("ajaxfile");

function openForm() {
    input.click();
    console.log(input)
}

const btnUpload = document.querySelector(".btnUpload")
btnUpload.addEventListener('click', openForm)