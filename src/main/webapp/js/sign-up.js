//Check exist login
$(document).ready(function () {
    $('#loginRegForm').blur(function () {
        $.ajax({
            url : 'poker?command=checkExistLogin',
            data : {
                login : $('#loginRegForm').val()
            },
            success : function (response) {
                if (response.isExistUsername === 'true') {
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
                if (response.isExistUsername === 'true') {
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