

generateOrderID();

function generateOrderID(){
    $("#txtOrderID").val("O00-001");
    $.ajax({
        url: "http://localhost:8081/backEnd/order?option=Id",
        method: "GET",
        success: function (resp) {
            let orderId = resp.orderId;
            let tempId = parseInt(orderId.split("-")[1]);
            tempId = tempId+1;
            if (tempId <= 9){
                $("#txtOrderID").val("O00-000"+tempId);
            }else if (tempId <= 99) {
                $("#txtOrderID").val("O00-00" + tempId);
            }else if (tempId <= 999){
                $("#txtOrderID").val("O00-0" + tempId);
            }else {
                $("#txtOrderID").val("O00-"+tempId);
            }
        }
    });

}

$('#txtOrderDate').val(new Date().toISOString().slice(0, 10));

function loadCustomerData() {
    $("#txtOrderCusID").empty();
    $("#txtOrderCusID").append($("<option></option>").attr("value", 0).text("Select-Customer"));
    let count = 0;
    $.ajax({
        url: "http://localhost:8081/backEnd/customer",
        method: "GET",
        success: function (res) {
            for (const customer of res.data) {
                $("#txtOrderCusID").append($("<option></option>").attr("value", count).text(customer.id));
                count++;
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
        }
    });
}

$("#txtOrderCusID").click(function () {

    let id = $("#txtOrderCusID option:selected").text();
    let name = $("#txtOrderCusName").val();
    let address = $("#txtOrderCusAddress").val();

    $.ajax({
        url: "http://localhost:8081/backEnd/customer",
        method: "GET",
        success: function (resp) {
            for (const customer of resp.data) {

                if (customer.id == id) {

                    name = customer.name;
                    address = customer.address;

                    $("#txtOrderCusName").val(name);
                    $("#txtOrderCusAddress").val(address);
                }

            }
        }
    });
});

function loadItemData() {
    $("#txtOrderItemCode").empty();
    $("#txtOrderItemCode").append($("<option></option>").attr("value", 0).text("Select-Item"));
    let count = 0;
    $.ajax({
        url: "http://localhost:8081/backEnd/item",
        method: "GET",
        success: function (res) {
            for (const item of res.data) {
                $("#txtOrderItemCode").append($("<option></option>").attr("value", count).text(item.txtItemId));
                count++;
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
        }
    });
}

$("#txtOrderItemCode").click(function () {

    let id = $("#txtOrderItemCode option:selected").text();
    let itemName = $("#txtOrderItemName").val();
    let itemQty = $("#txtOrderItemQtyOnHand").val();
    let itemPrice = $("#txtOrderItemPrice").val();

    $.ajax({
        url: "http://localhost:8081/backEnd/item?option=GetAll",
        method: "GET",
        success: function (resp) {
            for (const item of resp.data) {
                if (item.txtItemId == id) {

                    itemName = item.txtItemName;
                    itemQty = item.txtQty;
                    itemPrice = item.txtPrice;

                    $("#txtOrderItemName").val(itemName);
                    $("#txtOrderItemQtyOnHand").val(itemQty);
                    $("#txtOrderItemPrice").val(itemPrice);
                }
            }
        }
    });
});


