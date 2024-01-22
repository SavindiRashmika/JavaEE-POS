

$("#btnCustomer").click(function () {

    let formData = $("#customerForm").serialize();
    console.log(formData);
    $.ajax({
        url: "http://localhost:8080/backEnd/customer",
        method: "POST",
        data: formData,
        success: function (res) {
            console.log(res);
            if (res.status == 200) {
                loadAllCustomer();
                alert(res.message);
                resetCustomer();
            } else {
                console.log(res)
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            console.log(ob);
            console.log(textStatus);
            console.log(error);
        }
    });
});