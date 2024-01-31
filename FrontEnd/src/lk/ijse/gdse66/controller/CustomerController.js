

loadAllCustomer();

function loadAllCustomer(){
    $("#tblCustomer").empty();
    $.ajax({
        url:"http://localhost:8081/backEnd/customer",
        method: "GET",
        success: function (resp){
            console.log(typeof resp);
            for (const customer of resp.data) {
                let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td></tr>`;
                $("#tblCustomer").append(row);
            }
            bindClickEvent();
        }
    });
}

$("#btnCustomer").click(function () {

    let data = $("#customerForm").serialize();
    console.log(data);
    $.ajax({
        url: "http://localhost:8081/backEnd/customer",
        method: "POST",
        data: data,
        success: function (res) {
            console.log(res);
            if (res.status == 200) {
                loadAllCustomer();
                resetCustomer();
                alert(res.message);
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



$("#btnUpdate").click(function () {
    let cusOb = {
        id: $("#txtCustomerID").val(),
        name: $("#txtCustomerName").val(),
        address: $("#txtCustomerAddress").val()
    };

    $.ajax({
        url: "http://localhost:8081/backEnd/customer",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(cusOb),
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                resetCustomer();
                loadAllCustomer()
            } else if (res.status == 400) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        },
        error: function (ob, errorStus) {
            console.log(ob);
            console.log(errorStus);
        }
    });
});

function bindClickEvent(){
    $("#tblCustomer>tr").click(function (){
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();

        $("#txtCustomerID").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);

    });
}

$("#btnDelete").click(function () {
    let customerID = $("#txtCustomerID").val();

    $.ajax({
        url: "http://localhost:8081/backEnd/customer?txtCustomerID=" + customerID,
        method: "DELETE",

        success: function (res) {
            console.log(res);
            if (res.status == 200) {
                alert(res.message);
                resetCustomer();
                loadAllCustomer();
            } else if (res.status == 400) {
                alert(res.data);
            } else {
                alert(res.data);
            }

        },
        error: function (ob, status, t) {
            console.log(ob);
            console.log(status);
            console.log(t);
        }
    });
});

function resetCustomer() {
    $("#txtCustomerID").val("");
    $("#txtCustomerName").val("");
    $("#txtCustomerAddress").val("");
}

