

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
        }
    });
}

