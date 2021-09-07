async function uploadFile() {
    let formData = new FormData();
    formData.append("file", ajaxfile.files[0]);
    await fetch('poker?command=upload-photo', {
        method: "POST",
        body: formData
    });
    alert('The file upload with Ajax and Java was a success!');
}