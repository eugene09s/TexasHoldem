const tableOfUsers = document.querySelector('.table-of-users');

tableOfUsers.onclick = function (event) {
    let targetElement = event.target;
    if (targetElement.classList.contains('btn-action-ban')) {
        actionBanUser(targetElement);
    }
}

async function actionBanUser(btnElement) {
    let parentElement = btnElement.closest("tr");
    let spanDot = parentElement.children[4].children[0];
    let spanStatusUser = parentElement.children[4].children[1];
    let idElement = parentElement.firstElementChild;
    let idUser = idElement.innerHTML;
    let response;
    let btnEvent = btnElement.innerHTML.trim();
    console.log(btnEvent);
    switch (btnEvent) {
        case 'BAN': {
            response = await sendEventBan(idUser, 'ban');
            if (response.success) {
                spanStatusUser.innerHTML = response.message;
                changeStylesBtnAndDotByStatus(response.message, btnElement, spanDot);
            }
        }
            break;
        case 'UNBAN': {
            response = await sendEventBan(idUser, 'unban');
            if (response.success) {
                spanStatusUser.innerHTML = response.message;
                changeStylesBtnAndDotByStatus(response.message, btnElement, spanDot);
            }
        }
            break;
    }
}

function changeStylesBtnAndDotByStatus(status, btn, dot) {
    if (status == 'ACTIVE') {
        btn.innerHTML = 'BAN';
        btn.classList.remove('btn-outline-success');
        dot.classList.remove('text-danger');
        btn.classList.add('btn-outline-danger');
        dot.classList.add('text-success');
    } else if (status == 'BANNED') {
        btn.innerHTML = 'UNBAN';
        btn.classList.remove('btn-outline-danger');
        dot.classList.remove('text-success');
        btn.classList.add('btn-outline-success');
        dot.classList.add('text-danger');
    }
}

async function sendEventBan(data, action) {
    const response = await fetch('poker?command=action-' + action + '-user&id=' + data);
    return await response.json();
}