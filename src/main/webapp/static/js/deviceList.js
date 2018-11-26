var showMore = function () {
    if ($("#show-more").css("display") == 'block') {
        $("#model").val('');
        $("#batchSelect").val('');
        $("#startTime").val('');
        $("#endTime").val('');
        $("#versionSelect").val('');
        $("#categorySelect").val('');
        $("#userName").val('');
        $("#deviceNumber").val('');
    }
    $("#show-more").toggle();
}

var delDevice = function () {
    var reason = $("#delDevice_reason").val();
    var deviceUUID = $("#deviceEdit_deviceUUID").val();
    ajaxPost("/device/ledger/delete",
        {
            deviceUUID: deviceUUID,
            reason: reason
        },
        function (resp) {
            if (resp.success) {
                $("#editDeviceModal").modal('hide');
                showAlert("删除成功");
                reloadListData();
            } else {
                showAlert(resp.message);
            }
        });

}

var initAccount = function () {
    $('#qrCodeModal').modal('show');
    $("#qrCodeImage").addClass("hide");
}
//打开二维码
var initQrCode = function () {
    var _model = $('#i_model').val();
    var _batch = $('#i_batch').val();
    var _deviceNumber = $('#i_number').val();
    var _user = userId;
    var _affl = userOrganisation;
    // var _dept = $('#i_dept').val();
    var url = window.location.protocol + "//" + window.location.host + "/device/ledger/helmet/add?model=" + _model + "&batch=" + _batch + "&affiliationId=" + _affl + "&userId=" + _user + "&deviceNumber=" + _deviceNumber;
    // $("#qrcode_id").val(id);
    $("#qrCodeImage").addClass("hide");
    $('#qrCode canvas').remove();
    $('#qrCode').qrcode(url);
    var canvas = $('#qrCode canvas');
    var dataUrl = canvas[0].toDataURL();
    if (_deviceNumber == '') {
        console.log("warning");
        $("#warning").attr("style","display:block;position:absolute; z-index:2; left:40%; top:40%; font-size: x-large;color: red;\n" +
            "                    background-color: #f2dede;");
        $("#qrCodeImage").attr("src", dataUrl).removeClass("hide");
    }else{
        console.log("none");
        $("#warning").attr("style","display:none;position:absolute; z-index:2; left:40%; top:40%; font-size: x-large;color: red;\n" +
            "                    background-color: #f2dede;");
        $("#qrCodeImage").attr("src", dataUrl).removeClass("hide");
    }
    canvas.remove();
    // $("#qrCodeModalTitle").text("初始化账号");
    // $("#qrCodeModalBtn").text("手工初始化");
};


var regDevice = function (deviceUUID, userName, userId, versionId, version, model, batch, deviceNumber, affiliationId, affiliationName, categoryId, category) {

    newPagePager.deviceUUID = deviceUUID;
    newPagePager.userId = userId;
    $("#userSelect").html('');
    var dept = '';
    var userContent = $("#userContent");
    var _inner = "<div class=\"panel-body col-md-6\">\n" +
        "                <p id=\"p_number\" align=\"left\">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:" + deviceNumber + "<br></p>\n" +
        "                <p id=\"p_modal\" align=\"left\">型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:" + model + "<br></p>\n" +
        "                <p id=\"p_version\" align=\"left\">软件版本:" + version + "<br></p>\n" +
        // "                <p id=\"p_dept\" align=\"left\">持有部门:" + dept + "<br></p>\n" +
        "                <br>\n" +
        "\n" +
        "            </div>\n" +
        "            <div class=\"panel-body col-md-6\">\n" +
        "                <p id=\"p_category\" align=\"left\">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:" + category + "<br></p>\n" +
        "                <p id=\"p_batch\" align=\"left\">批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:" + batch + "<br></p>\n" +
        "                <p id=\"p_company\" align=\"left\">持有单位:" + affiliationName + "<br></p>\n" +
        "                <p id=\"p_user\" align=\"left\">持有用户:" + userName + "<br></p>\n" +
        "                <br>\n" +
        "            </div>";
    userContent.html(_inner);
    var selectObj = $("#userSelect");
    $.post("/user/listnopage",
        {
            organisation: affiliationId,
            department: dept
        },
        function (resp) {
            if (resp.success) {
                $.each(resp.data, function (idx, usr) {
                    selectObj.append('<option value="' + usr.id + '">' + usr.name + '</option>');
                });
                $("#userSelect").val(userId);
                $("#companySelect").val(affiliationId);
                $('#regDeviceModal').modal('show');
            } else {
                showAlert('载入用户失败');
            }
        });
};

var changeUser = function () {
    var dept = '';
    var selectObj = $("#userSelect");
    $.post("/user/listnopage",
        {
            organisation: $("#companySelect").val(),
            department: dept
        },
        function (resp) {
            if (resp.success) {
                selectObj.html('');
                $.each(resp.data, function (idx, usr) {
                    selectObj.append('<option value="' + usr.id + '">' + usr.name + '</option>');
                });

            } else {
                showAlert('载入用户失败');
            }
        });
};

var saveReg = function () {
    var _deviceUUID = newPagePager.deviceUUID;
    var _userId = $("#userSelect").val();
    var _company = $("#companySelect").val();
    if (_userId == newPagePager.userId) {
        showAlert("不可选择原持有人！");
        return;
    }
    var object = {
        id: '',
        affiliationId: _company,
        category: '',
        deviceNumber: '',
        deviceUUID: _deviceUUID,
        model: '',
        batch: '',
        version: '',
        userId: _userId,
        flag: '',
        createTime: '',
        endTime: '',
        updateTime: '',
        startTime: ''
    };
    $.ajax({
        type: "POST",
        url: "/device/ledger/update",
        data: JSON.stringify(object),//将对象序列化成JSON字符串
        dataType: "json",
        contentType: 'application/json;charset=utf-8', //设置请求头信息(必须)
        success: function (resp) {
            if (resp.success) {
                showAlert('变更成功');
                reloadListData();
            } else {
                showAlert(resp.message);
            }
        },
        error: function (resp) {

        }
    });
    $('#regDeviceModal').modal('hide');
};

var saveRegDevice = function () {

    var deviceUUID = $("#deviceEdit_deviceUUID").val();
    var batch = $("#edit_batchSelect").val();
    var model = $("#edit_model").val();

    var object = {
        id: '',
        affiliationId: '',
        category: '',
        deviceNumber: '',
        deviceUUID: deviceUUID,
        model: model,
        batch: batch,
        version: '',
        userId: '',
        flag: '',
        createTime: '',
        endTime: '',
        updateTime: '',
        startTime: '',

    };
    $.ajax({
        type: "POST",
        url: "/device/ledger/update",
        data: JSON.stringify(object),//将对象序列化成JSON字符串
        dataType: "json",
        contentType: 'application/json;charset=utf-8', //设置请求头信息(必须)
        success: function (resp) {
            if (resp.success) {
                showAlert('设置成功');
                $('#editDeviceModal').modal('hide');
                reloadListData();
            } else {
                showAlert(resp.message);
            }
        },
        error: function (resp) {

        }
    });

};

var regDeviceInfo = function (deviceUUID, userName, userId, versionId, version, model, batch, deviceNumber, affiliationId, affiliationName, categoryId, category) {
    $("#deviceEdit_deviceUUID").val(deviceUUID);
    $("#delDevice_reason").val('');
    $("#saveModal").attr("style", "display:block");
    $("#delDeviceModal").attr("style", "width:100%;display:none");
    $("#edit_userName").val(userName);
    $("#edit_versionSelect").val(version);
    $("#edit_model").val(model);
    $("#edit_batchSelect").val(batch);
    $("#edit_categorySelect").val(category);
    $('#editDeviceModal').modal('show');
};


var printQrCode = function () {
    $('#qrCode').jqprint({
        debug: false,
        importCSS: true,
        printContainer: true,
        operaSupport: false
    })
};

function reloadListData() {
    loadData(newPagePager.currentPage);
}

var loadData = function (page) {

    var url = "/device/ledger/get/keyword";
    if (newPagePager.startTime != '') {
        if (newPagePager.startTime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").length == 8) {
            newPagePager.startTime += ' 00:00:00';
        }
    }
    if (newPagePager.endTime != '') {
        if (newPagePager.endTime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").length == 8) {
            newPagePager.endTime += ' 23:59:59';
        }
    }
    ajaxPost(url,
        {
            page: page,
            affiliationId: newPagePager.regDept_affiliationId,
            userName: newPagePager.userName,
            categoryId: newPagePager.regDept_categoryId,
            versionId: newPagePager.regDept_versionId,
            model: newPagePager.model,
            batch: newPagePager.batch,
            startTime: newPagePager.startTime,
            endTime: newPagePager.endTime,
            deviceNumber: newPagePager.deviceNumber,
            flag: 1
        },
        function (resp) {
            if (resp.success) {
                var list = resp.data.list;
                newPagePager.page = resp.data.currentPage || 1;
                newPagePager.pageCount = resp.data.pageTotal || 1;
                newPagePager.total = resp.data.count || 0;
                newPagePager.pageSize = resp.data.pageSize || 10;
                var _inner = '';
                var deviceListContent = $("#deviceListContent");
                for (var i = 0; i < list.length; i++) {
                    var _item = list[i];
                    if (_item.createTime == null) {
                        _item.createTime = '';
                    }
                    _inner += "<tr>" +
                        '<td>' + (i + 1) + '</td>' +
                        '<td>' + _item.deviceNumber + '</td>' +
                        '<td>' + _item.category + '</td>' +
                        '<td>' + _item.model + '</td>' +
                        '<td>' + _item.batch + '</td>' +
                        '<td>' + _item.createTime + '</td>' +
                        '<td>' + _item.userName + '</td>' +
                        '<td>' + _item.affiliationName + '</td>' +
                        '<td>' + _item.version + '</td>' +
                        '<td>' +
                        '<a onclick="regDeviceInfo(\'' + _item.deviceUUID + '\',\'' + _item.userName + '\',\'' + _item.userId + '\',\'' + _item.versionId + '\',\'' + _item.version + '\',\'' + _item.model + '\',\'' + _item.batch + '\',\'' + _item.deviceNumber + '\',\'' + _item.affiliationId + '\',\'' + _item.affiliationName + '\',\'' + _item.categoryId + '\',\'' + _item.category + '\')" class="label label-success">修改</a>' +
                        '<a onclick="regDevice(\'' + _item.deviceUUID + '\',\'' + _item.userName + '\',\'' + _item.userId + '\',\'' + _item.versionId + '\',\'' + _item.version + '\',\'' + _item.model + '\',\'' + _item.batch + '\',\'' + _item.deviceNumber + '\',\'' + _item.affiliationId + '\',\'' + _item.affiliationName + '\',\'' + _item.categoryId + '\',\'' + _item.category + '\')" class="label label-info">变更持有人</a>';

                    if (userOrganisation == 1) {
                        _inner += '<a id="unbind" onclick="unbinded(\'' + _item.deviceUUID + '\',\'' + _item.affiliationId + '\')" class="label label-danger">注销</a>' +
                            '</td>' +
                            '</tr>';
                    } else {
                        _inner += '</td>' +
                            '</tr>';
                    }

                }
                deviceListContent.html("");
                deviceListContent.append(_inner);


            } else {
                showAlert(resp.message);
            }

            makePage(newPagePager.page, newPagePager.pageCount);
        });

}

var doSearch = function () {
    var regDept_affiliationId = $("#afflSelect option:selected").val();
    var userName = $("#userName").val();
    var regDept_categoryId = $("#categorySelect").val();
    var regDept_versionId = $("#versionSelect").val();
    var model = $("#model").val();
    var batch = $("#batchSelect option:selected").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var deviceNumber = $("#deviceNumber").val();

    newPagePager.regDept_affiliationId = regDept_affiliationId;
    newPagePager.userName = userName;
    newPagePager.regDept_categoryId = regDept_categoryId;
    newPagePager.regDept_versionId = regDept_versionId;
    newPagePager.model = model;
    newPagePager.batch = batch;
    newPagePager.startTime = startTime;
    newPagePager.endTime = endTime;
    newPagePager.deviceNumber = deviceNumber;

    loadData(1);
}

var initBtnEvent = function () {
    //搜
    $("#search-button").off('click').click(doSearch);
    //页签切换
    $("#saleStateList>li").click(function (event) {
        //此事件触发时，active的还没变，此时执行搜索会导致搜索旧的tab数据
        //所以延迟一下再执行
        setTimeout(doSearch, 300);
    });
    $("#more-button").off('click').click(showMore);
    $("#add-button").off('click').click(function () {
        initAccount();
        initQrCode();
    });
}

function makePage(page, pageCount) {
    newPagePager.currentPage = 1;
    if (page > pageCount) {
        newPagePager.currentPage = pageCount;
    } else {
        newPagePager.currentPage = page;
    }

    $("#new-page-pager").attr("data-page", newPagePager.currentPage);

    if (newPagePager.currentPage <= 1) {
        $("#prePageButton").addClass("disabled").prop("disabled", true);
    } else {
        $("#prePageButton").removeClass("disabled").prop("disabled", false);
    }

    if (newPagePager.currentPage >= pageCount) {
        $("#nextPageButton").addClass("disabled").prop("disabled", true);
    } else {
        $("#nextPageButton").removeClass("disabled").prop("disabled", false);
    }

    $("#toPageButton").off('click').click(function () {
        var toPage = $.trim($("#toPage").val());
        if (isNaN(toPage)) {
            return;
        }
        loadData(toPage, "");
    });
    $("#prePageButton").off('click').click(function () {
        loadData(newPagePager.currentPage - 1, "");
    });
    $("#nextPageButton").off('click').click(function () {
        loadData(newPagePager.currentPage + 1, "");
    });

    $("#pageInfo").text("共 " + newPagePager.total + " 条数据 , 第 " + newPagePager.currentPage + " 页 , 共 " + newPagePager.pageCount + " 页");
    return newPagePager.currentPage;
}

function afflReg() {
    console.log("afflReg Start");
    var selectObj1 = $("#i_affl1");
    var selectObj2 = $("#afflSelect");
    var selectObj3 = $("#companySelect");
    $.post("/company/listnopage", {}, function (resp) {
        console.log("afflReg post Start");
        if (resp.success) {
            $.each(resp.data, function (idx, affl) {
                console.log(affl.id + "---------" + affl.companyName);
                if (userOrganisation == 1) {
                    selectObj1.append('<option value="' + affl.id + '">' + affl.companyName + '</option>');
                    selectObj2.append('<option value="' + affl.id + '">' + affl.companyName + '</option>');
                    selectObj3.append('<option value="' + affl.id + '">' + affl.companyName + '</option>');
                } else {
                    if (userOrganisation == affl.id) {
                        $("#sl1").html('');
                        $("#sl1").append('<input type="text" id="afflSelect" value="' + affl.id + '" style="display: none" /><input type="text" class="form-control" value="' + affl.companyName + '" readonly />');
                        $("#sl2").html('');
                        $("#sl2").append('<input type="text" id="i_affl1" value="' + affl.id + '" style="display: none" /><input type="text" class="form-control" value="' + affl.companyName + '" readonly />');
                        $("#sl3").html('');
                        $("#sl3").append('<input type="text" id="companySelect" value="' + affl.id + '" style="display: none" /><input type="text" class="form-control" value="' + affl.companyName + '" readonly />');
                    }
                }
            });
            selectObj1.attr("data-init", "true");
            selectObj2.attr("data-init", "true");

        } else {
            showAlert('载入单位列表失败');
        }

    });


}

function userReg() {
    var afflId = $("#i_affl1").val();
    var dept = $("#i_dept").val();
    if (dept == null) {
        dept = '';
    }
    console.log("userReg Start");
    var selectObj1 = $("#userSelect1");
    $.post("/user/listnopage",
        {
            organisation: afflId,
            department: dept
        },
        function (resp) {
            console.log("userReg post Start");
            if (resp.success) {
                selectObj1.html("");
                $.each(resp.data, function (idx, usr) {
                    console.log(resp.data)
                    selectObj1.append('<option value="' + usr.id + '">' + usr.name + '</option>');
                });
            } else {
                showAlert('载入用户列表失败');
            }

        });
}

function deptReg() {
    var afflId = $("#i_affl1").val();

    console.log("deptReg Start");
    var selectObj1 = $("#i_dept");
    var selectObj2 = $("#userSelect1");
    $.post("/user/listdept",
        {
            organisation: afflId
        },
        function (resp) {
            console.log("deptReg post Start");
            if (resp.success) {
                selectObj1.html("");
                $.each(resp.data, function (idx, dept) {
                    console.log(resp.data)
                    selectObj1.append('<option value="' + dept + '">' + dept + '</option>');
                });
            } else {
                showAlert('载入部门列表失败');
            }

        });
    $.post("/user/listnopage",
        {
            organisation: afflId,
            department: ''
        },
        function (resp) {
            console.log("userReg post Start");
            if (resp.success) {
                selectObj2.html("");
                $.each(resp.data, function (idx, usr) {
                    console.log(resp.data)
                    selectObj2.append('<option value="' + usr.id + '">' + usr.name + '</option>');
                });
            } else {
                showAlert('载入用户列表失败');
            }

        });

}

function cateReg() {
    console.log("cateReg Start");
    var selectObj = $("#categorySelect");
    $.post("/dictionary/device/category", {}, function (resp) {
        console.log("cateReg post Start");
        if (resp.success) {
            $.each(resp.data, function (idx, category) {
                selectObj.append('<option value="' + category.categoryId + '">' + category.category + '</option>');
            });
            selectObj.attr("data-init", "true");
        } else {
            showAlert('载入品类列表失败');
        }

    });
}

function verReg() {
    console.log("verReg Start");
    var selectObj = $("#versionSelect");
    $.post("/dictionary/device/version", {}, function (resp) {
        console.log("verReg post Start");
        if (resp.success) {
            $.each(resp.data, function (idx, ver) {
                selectObj.append('<option value="' + ver.id + '">' + ver.version + '</option>');
            });
            selectObj.attr("data-init", "true");
        } else {
            showAlert('载入版本列表失败');
        }

    });
}

function batchReg() {
    console.log("batchReg Start");
    var selectObj1 = $("#batchSelect");
    var selectObj2 = $("#edit_batchSelect");
    var selectObj3 = $("#i_batch");
    $.post("/dictionary/device/batch", {}, function (resp) {
        console.log("batchReg post Start");
        if (resp.success) {
            $.each(resp.data, function (idx, b) {
                selectObj1.append('<option value="' + b.batch + '">' + b.batch + '</option>');
                selectObj2.append('<option value="' + b.batch + '">' + b.batch + '</option>');
                selectObj3.append('<option value="' + b.batch + '">' + b.batch + '</option>');
            });
        } else {
            showAlert('载入批次列表失败');
        }

    });
}


function modelReg() {
    console.log("modelReg Start");
    var selectObj1 = $("#model");
    var selectObj2 = $("#i_model");
    var selectObj3 = $("#edit_model");
    $.post("/dictionary/device/model", {}, function (resp) {
        console.log("modelReg post Start");
        if (resp.success) {
            $.each(resp.data, function (idx, m) {
                selectObj1.append('<option value="' + m.model + '">' + m.model + '</option>');
                selectObj2.append('<option value="' + m.model + '">' + m.model + '</option>');
                selectObj3.append('<option value="' + m.model + '">' + m.model + '</option>');
            });
        } else {
            showAlert('载入批次列表失败');
        }

    });
}

function unbinded(deviceUUID, affiliationId) {
    var _deviceUUID = deviceUUID;
    // if (affiliationId != 1) {
    //     showAlert("没有权限解绑")
    //     return;
    // }
    if (confirm("确定注销这个设备吗?")) {
        $.post("/device/ledger/clear", {deviceUUID: _deviceUUID}, function (resp) {
            if (resp.success) {
                showAlert("注销成功")
                reloadListData();
            } else {
                showAlert(resp.message);
            }
        })
    }
}
$(function () {
    initBtnEvent();
    doSearch();
    verReg();
    cateReg();
    afflReg();
    modelReg();
    batchReg();
});