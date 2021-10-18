const tableOfUsers = document.querySelector('.table-of-users');

tableOfUsers.onclick = function (event) {
    let targetElement = event.target;
    if (targetElement.classList.contains('btn-action-ban')) {
        actionBanUser(targetElement);
        return;
    }
    if (targetElement.classList.contains('btn-action-save-balance')) {
        actionChangeBalance(targetElement);
        return;
    }

    const parentElement = targetElement.closest("tr")
    const divInput = parentElement.lastElementChild.firstElementChild;
    const inputMoney = divInput.children[1];
    if (targetElement.classList.contains('btn-minus')) {
        let num = Number.parseFloat(inputMoney.value);
        if (num > 0) {
            inputMoney.value = --num;
        }
        return;
    }
    if (targetElement.classList.contains('btn-plus')) {
        let num = Number.parseFloat(inputMoney.value);
        inputMoney.value = ++num;
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

async function sendEventBan(id, action) {
    const response = await fetch('poker?command=action-' + action + '-user&id=' + id);
    return await response.json();
}

async function actionChangeBalance(btnElement) {
    let parentElement = btnElement.closest("tr");
    let idElement = parentElement.firstElementChild;
    let idUser = idElement.innerHTML;
    let divInputGroup = parentElement.children[5].children[0];
    let inputBalance = divInputGroup.children[1];
    let response = await sendEventChangeBalance(idUser, inputBalance.value);
    let btnSave = divInputGroup.lastElementChild;
    let btnValue = btnSave.innerHTML;
    if (response.success) {
        btnSave.classList.remove('btn-primary');
        btnSave.innerHTML = 'Success';
        btnSave.classList.add('btn-success');
        inputBalance.value = response.message;
    } else {
        btnSave.classList.remove('btn-primary');
        btnSave.innerHTML = 'Error';
        btnSave.classList.add('btn-danger');
        inputBalance.classList.add('border-danger');
    }
    setTimeout(function () {
        btnSave.classList.remove('btn-success');
        btnSave.classList.remove('btn-danger');
        btnSave.classList.add('btn-primary');
        btnSave.innerHTML = btnValue;

        inputBalance.classList.remove('border-danger');
        inputBalance.classList.add('border-primary');
    }, 3000);
}

async function sendEventChangeBalance(id, money) {
    const response = await fetch('poker?command=action-change-balance&id=' + id + '&money=' + money);
    return await response.json();
}