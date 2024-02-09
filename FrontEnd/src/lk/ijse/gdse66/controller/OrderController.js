

// generateOrderID();

$('#txtOrderDate').val(new Date().toISOString().slice(0, 10));

function loadCustomerData() {
    $("#txtOrderCusID").empty();
    $.ajax({
        url: "http://localhost:8081/backEnd/customer",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(res);

            for (let i of res.data) {
                let id = i.id;

                $("#txtOrderCusID").append(`<option>${id}</option>`);
            }
            // generateOrderID();
            console.log(res.message);
        }, error: function (error) {
            let message = JSON.parse(error.responseText).message;
            console.log(message);
        }

    });
    /*$("#txtOrderCusID").empty();
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
    });*/
}

$("#txtOrderCusID").click(function () {
    $("#txtOrderCusID").val();
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
    $.ajax({
        url: "http://localhost:8081/backEnd/item",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(res);

            for (let i of res.data) {
                let code = i.txtItemId;

                $("#txtOrderItemCode").append(`<option>${code}</option>`);
            }
            console.log(res.message);
        }, error: function (error) {
            let message = JSON.parse(error.responseText).message;
            console.log(message);
        }
    /*$("#txtOrderItemCode").empty();
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
        }*/
    });
}

$("#txtOrderItemCode").click(function () {

    let id = $("#txtOrderItemCode option:selected").text();
    let itemName = $("#txtOrderItemName").val();
    let itemQty = $("#txtOrderItemQtyOnHand").val();
    let itemPrice = $("#txtOrderItemPrice").val();

    $.ajax({
        url: "http://localhost:8081/backEnd/item?option=get",
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

function itemTextFieldClear() {
    loadItemData();
    $("#txtOrderItemQtyOnHand").val("");
    $("#txtOrderItemPrice").val("");
    $("#txtOrderItemName").val("");
    $("#txtOrderQty").val("");
}

function customerTextFieldClear() {
    loadCustomerData();
    $("#txtOrderCusName").val("");
    $("#txtOrderCusAddress").val("");
}

/*function loadAllOrders(){
    $("#orderTable").empty();
    $.ajax({
        url: "http://localhost:8081/backEnd/order?option=get",
        method: "GET",
        success: function (resp) {
            for (const orders of resp.data) {
                console.log("loadAllOrder for ekat awa ");
                let row = `<tr><td>${orders.oid}</td><td>${orders.customerID}</td><td>${orders.date}</td><td>
                ${orders.total}</td><td>${orders.discount}</td><td>${orders.subTotal}</td></tr>`;
                $("#orderTable").append(row);

            }
        }
    });
}

function loadOrderDetail() {

    itemCode = $("#txtOrderItemCode option:selected").text();
    itemName = $("#txtOrderItemName").val();
    itemPrice = $("#txtOrderItemPrice").val();
    itemQtyOnHand = $("#txtOrderItemQtyOnHand").val();
    itemOrderQty = $("#txtOrderQty").val();

    let total = itemPrice * itemOrderQty;

    $("#addToCartTable").append("<tr>" +
        "<td>" + itemCode + "</td>" +
        "<td>" + itemName + "</td>" +
        "<td>" + itemPrice + "</td>" +
        "<td>" + itemOrderQty + "</td>" +
        "<td>" + total + "</td>" +
        "</tr>");

    manageDiscount();
    // bindOrderClickEvent();

}*/

function generateOrderID(){
    $("#txtOrderID").val("O00-001");
    $.ajax({
        url: "http://localhost:8081/backEnd/order?option=Id",
        method: "GET",
        success: function (resp) {
            let orderId = resp.oid;
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

loadAllOrders();
loadAllOrderDetails();
$("#addToCartTable").empty();

function loadCartTable() {
    itemCode = $("#txtOrderItemCode").val();
    itemName = $("#txtOrderItemName").val();
    itemPrice = $("#txtOrderItemPrice").val();
    itemOrderQty = $("#txtOrderQty").val();


    let total = itemPrice * itemOrderQty;
    let row = `<tr><td>${itemCode}</td><td>${itemName}</td><td>${itemPrice}</td><td>${itemOrderQty}</td><td>${total}</td></tr>`;

    $("#addToCartTable").append(row);
}

function loadAllOrders() {
    $("#orderTable").empty();
    $.ajax({
        url: "http://localhost:8081/backEnd/order?option=get",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(res);

            for (let i of res.data) {
                let orderId = i.oid;
                let date = i.date;
                let cusId = i.customerID;
                let total = i.total;
                let subTotal = i.subTotal;
                let discount = i.discount;

                let row = "<tr><td>" + orderId + "</td><td>" + date + "</td><td>" + cusId + "</td><td>" + total + "</td><td>" + subTotal + "</td><td>" + discount + "</td></tr>";
                $("#orderTable").append(row);
            }
            console.log(res.message);
        }, error: function (error) {
            let message = JSON.parse(error.responseText).message;
            console.log(message);
        }

    });
}

function loadAllOrderDetails() {
    $("#orderDetailTable").empty();
    $.ajax({
        url: "http://localhost:8081/backEnd/order?option=orderDetail",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(res);

            for (let i of res.data) {
                let OrderId = i.oid;
                let code = i.itemCode;
                let qty = i.qty;
                let unitPrice = i.unitPrice;
                let total = i.total;

                let row = "<tr><td>" + OrderId + "</td><td>" + code + "</td><td>" + qty + "</td><td>" + unitPrice + "</td><td>" + total + "</td></tr>";
                $("#orderDetailTable").append(row);
            }
            console.log(res.message);
        }, error: function (error) {
            let message = JSON.parse(error.responseText).message;
            console.log(message);
        }

    });
}

$("#btnAddToCart").click(function () {

    if ($("#txtOrderCusName").val() == '') {
        alert("Please Select Customer");
    } else if ($("#txtOrderItemName").val() == '') {
        alert("Please Select Item");
    } else if ($("#txtOrderQty").val() == '') {
        alert("Please Enter Valid Quantity");
    }else if (parseInt($("#txtOrderQty").val()) > parseInt($("#txtOrderItemQtyOnHand").val())){
        alert("Please Check Stock");
    }else{
        let duplicate = false;

        for (let i = 0; i < $("#addToCartTable tr").length; i++) {
            if ($("#txtOrderItemCode option:selected").text() == $("#addToCartTable tr").children(':nth-child(1)')[i].innerText) {
                duplicate = true;
            }
        }

        if (duplicate != true) {
            loadCartTable();
            loadAllOrderDetails();
            loadAllOrders();
            minusQty($("#txtOrderQty").val());
            manageTotal($("#txtOrderQty").val() * $("#txtOrderItemPrice").val());
            manageDiscount();
            itemTextFieldClear();
            customerTextFieldClear();
            // bindOrderClickEvent();

        } else if (duplicate == true) {

            manageQuantity(tableRow.children(':nth-child(4)').text(), $("#txtOrderQty").val());
            $(tableRow).children(':nth-child(4)').text($("#txtOrderQty").val());

            updateManageTotal(tableRow.children(':nth-child(5)').text(), $("#txtOrderQty").val() * $("#txtOrderItemPrice").val());
            $(tableRow).children(':nth-child(5)').text($("#txtOrderQty").val() * $("#txtOrderItemPrice").val());

            itemTextFieldClear();
        }

        // bindOrderClickEvent();
    }
});

function manageQuantity(prevQty, nowQty) {
    var prevQty = parseInt(prevQty);
    var nowQty = parseInt(nowQty);
    var availableQty = parseInt($("#txtOrderItemQtyOnHand").val());

    availableQty += prevQty;
    availableQty -= nowQty;

    $("#txtOrderItemQtyOnHand").val(availableQty);
}

function manageDiscount() {
    var net = ($("#total").text());
    var discount = 0;

    if (net > 500 && net < 999) {
        discount = 2;
        ($("#txtDiscount").val(discount));
    } else if (net > 1000 && net < 2999) {
        discount = 4;
        ($("#txtDiscount").val(discount));
    } else if (net > 3000 && net < 4999) {
        discount = 5;
        ($("#txtDiscount").val(discount));
    } else if (net > 5000 && net < 9999) {
        discount = 8;
        ($("#txtDiscount").val(discount));
    } else if (net > 10000) {
        discount = 10;
        ($("#txtDiscount").val(discount));
    }

    var subTotal = (net * discount) / 100;
    subTotal = net - subTotal;
    parseInt($("#subtotal").text(subTotal));

}

var total = 0;
function manageTotal(amount) {
    total += amount;
    ($("#total").text(total));

    manageDiscount();
}

function updateManageTotal(prvTotal, nowTotal) {
    total -= prvTotal;
    total += nowTotal;

    ($("#total").text(total));

    manageDiscount();
}

function minusQty(orderQty) {
    var minusQty = parseInt(orderQty);
    var manageQty = parseInt($("#txtOrderItemQtyOnHand").val());

    manageQty = manageQty - minusQty;

    $("#txtOrderItemQtyOnHand").val(manageQty);
    // bindOrderClickEvent();
}


/*$("#orderTable").empty();
function loadAllOrders(){
    var orderID = $("#txtOrderID").val();
    var orderDate = $("#txtOrderDate").val();
    var customerId = $("#txtOrderCusID option:selected").text();
    var  total = $("#total").text();
    var subTotal =  $("#subtotal").text();
    var discount =  $("#txtDiscount").text();

    $("#orderTable").append("<tr>" +
        "<td>" + orderID + "</td>" +
        "<td>" + customerId + "</td>" +
        "<td>" + orderDate + "</td>" +
        "<td>" + total + "</td>" +
        "<td>" + subTotal + "</td>" +
        "<td>" + discount + "</td>" +
        "</tr>");
        }*/

var tableRow;
var itemCode;
var itemName;
var itemPrice;
var itemQtyOnHand;
var itemOrderQty;

$("#addToCartTable").empty();

loadAllOrderDetails();

function manageBalance() {
    let balance = 0;
    let subtotal = parseInt($("#subtotal").text());
    let cash = parseInt($("#txtCash").val());

    balance = cash - subtotal;

    parseInt($("#txtBalance").val(balance));
}


$("#btnSubmitOrder").click(function () {

    let orderDetails = [];

    if (parseInt($("#subtotal").text()) > parseInt($("#txtCash").val())){
        alert("Please need more money");
        $("#txtCash").val('');
    }else{
        var discount = parseInt($("#total").text()) - parseInt($("#subtotal").text());


        for (let i = 0; i < $("#orderDetailTable > tr").length; i++) {
            var OrderDetail = {
                oid : $("#txtOrderID").val(),
                itemCode : $("#orderDetailTable> tr").children(':nth-child(1)')[i].innerText,
                qty : $("#orderDetailTable > tr").children(':nth-child(4)')[i].innerText,
                unitPrice : $("#orderDetailTable > tr").children(':nth-child(3)')[i].innerText,
                total : $("#orderDetailTable > tr").children(':nth-child(5)')[i].innerText

            }
            orderDetails.push(OrderDetail);
        }

        var orderOb = {
            oid:$("#txtOrderID").val(),
            customerID:$("#txtOrderCusID option:selected").text(),
            date:$("#txtOrderDate").val(),
            total:$("#total").text(),
            subTotal:$("#subtotal").text(),
            discount:$("#txtDiscount").val(),
            OrderDetail : orderDetails
        };

        if ($("#txtCash").val() == '') {
            alert("Please Enter Cash");
        }else {
            $.ajax({
                url: "http://localhost:8081/backEnd/order",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(orderOb),
                success: function (resp) {
                    manageBalance();
                    itemTextFieldClear();
                    customerTextFieldClear();
                    // generateOrderID();
                    alert("Successfully Added");

                },
                error: function (ob, textStatus, error) {
                    console.log("data enne na")
                    alert(textStatus);
                }
            });

        }
    }

});
