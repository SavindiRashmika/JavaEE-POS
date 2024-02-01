

loadAllItem();

$("#btnItem").click(function (){
    let itemOb = {
        "txtItemId": $("#txtItemId").val(),
        "txtItemName": $("#txtItemName").val(),
        "txtQty": $("#txtQty").val(),
        "txtPrice": $("#txtPrice").val()
    };

    $.ajax({
        url:"http://localhost:8081/backEnd/item",
        method:"POST",
        contentType: "application/json",
        data: JSON.stringify(itemOb),
        success: function (resp){
            if (resp.status == 200){
                loadAllItem();
                alert(resp.message);
                resetItem();
            }else{
                alert(resp.data);
            }
        },
        error: function (ob, textStatus, error) {
            console.log(ob);
            console.log(textStatus);
            console.log(error);
        }
    });
});

$("#btnItemGet").click(function (){
    resetItem();
    loadAllItem();
});


function resetItem(){
    $("#txtItemId").val("");
    $("#txtItemName").val("");
    $("#txtQty").val("");
    $("#txtPrice").val("");
}

function loadAllItem(){
    $("#tblItem").empty();
    $.ajax({
        url:"http://localhost:8081/backEnd/item",
        method:"GET",
        success:function (data){
            for (const item of data.data){
                let row = `<tr><td>${item.txtItemId}</td><td>${item.txtItemName}</td><td>${item.txtQty}</td><td>${item.txtPrice}</td></tr>`;
                $("#tblItem").append(row);

            }
            bindClickEvent();
        }
    });
}

function bindClickEvent() {
    $("#tblItem>tr").click(function () {

        let id = $(this).children().eq(0).text();
        let name= $(this).children().eq(1).text();
        let qtyOnHand = $(this).children().eq(2).text();
        let price = $(this).children().eq(3).text();

        $("#txtItemId").val(id);
        $("#txtItemName").val(name);
        $("#txtQty").val(qtyOnHand);
        $("#txtPrice").val(price);

    });
}

$("#btnItemUpdate").click(function (){
    let itemOb = {
        txtItemId: $("#txtItemId").val(),
        txtItemName: $("#txtItemName").val(),
        txtQty: $("#txtQty").val(),
        txtPrice: $("#txtPrice").val()
    };
    $.ajax({
        url: "http://localhost:8081/backEnd/item",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(itemOb),
        success: function (res){
            if (res.status == 200){
                alert(res.message);
                resetItem();
                loadAllItem();
            } else if (res.status == 400){
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
