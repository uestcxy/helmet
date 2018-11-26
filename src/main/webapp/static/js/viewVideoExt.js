function initMapData() {
    //初始化地图
    initializeMap(10);
    //初始化数据
    var lonlat = videoExtPage.lonlat;
    if (lonlat != null && lonlat[0] > 0 && lonlat[1] > 0) {
        console.debug('视频定位信息显示在地图上');
        createMapMarker("video_" + videoExtPage.videoId, null, lonlat[0], lonlat[1], "");
        setMapCenter(lonlat[0], lonlat[1], 12);
    } else {
        console.debug('视频没有定位信息,不显示定位');
    }
}

var initVideoText = function () {
    var extend = videoExtPage.videoExtend;
    var txt = (extend.audioEditText == undefined || $.trim(extend.audioEditText) == "") ? extend.audioOrigText : extend.audioEditText;
    $('#videoText').val(txt);
    // $('#videoText').trigger('autoresize');
}

var initVideoKeyword = function () {
    $.each(videoExtPage.keywordList, function (idx, kw) {
        addToKeywordArea(kw.keywordName,kw.keywordId);
    });
}

var checkAddKeyword = function (kwToAdd) {
    var kwExists = false;
    $.each(videoExtPage.keywordList, function (idx, kw) {
        if (kw.keywordName == kwToAdd) {
            kwExists = true;
            return false;
        }
    });

    return !kwExists;
}

var addToKeywordArea = function (kwStr,kwId) {
    var kwChip = $('<div class="chip btn btn-primary" data-key="'+kwId+'">'+kwStr+'</div>');
    var closeBtn = $('<i class="myClose fa fa-close" title="点击移除此关键词" style="display: none;"></i>');
    kwChip.append(closeBtn);
    $("#keywordArea").append(kwChip);
    closeBtn.click(function(){
        var keywordId = $(this).parents("div.chip").attr("data-key");
        removeVideoKeyword(keywordId);
    });
    if($("#keywordArea i:first").is(":visible")){
        closeBtn.css("display","block");
    }
}

var initVideoExtEvent = function () {
    $("#keyword").off('keydown').keydown(function (event) {
        if (event.keyCode == 13) {
            var txt = $.trim($("#keyword").val());
            if (txt != "") {
                var isNewKw = checkAddKeyword(txt);
                if (isNewKw) {
                    addKeywordToVideo(txt);
                }else{
                    $("#keyword").val("");
                    showAlert("关键词已添加");
                }
            }
        }
    });
    $("#videoTextSaveBtn").off('click').click(function () {
        var txt = $.trim($("#videoText").val());
        saveVideoText(txt);
    });
}

var saveVideoText = function (text) {
    ajaxPost("/videoext/savetext/" + videoExtPage.videoId, {editText: text}, function (resp) {
        if(resp.success){
            showAlert("保存成功");
        }else{
            showAlert("保存失败."+resp.message);
        }
    });
}

var addKeywordToVideo = function (keyword) {
    ajaxPost("/videoext/addkeyword/" + videoExtPage.videoId, {"keyword": keyword}, function (resp) {
        if(resp.success){
            var kw = resp.data;
            addToKeywordArea(kw.keywordName,kw.keywordId);
            videoExtPage.keywordList.push(kw);
            $("#keyword").val("");
            showAlert("添加成功");
        }else{
            showAlert("添加关键词失败."+resp.message);
        }
    });
}

var removeVideoKeyword = function (keywordId) {
    ajaxPost("/videoext/removekeyword/" + videoExtPage.videoId, {"keywordId": keywordId}, function (resp) {
        if(resp.success){
            $("div[data-key="+keywordId+"]").remove();
            $.each(videoExtPage.keywordList,function (idx,kw) {
               if(kw.keywordId == keywordId){
                   //移除数组中这个关键词缓存
                   videoExtPage.keywordList.splice(idx,1);
                   return false;
               }
            });
            showAlert("移除关键词成功");
        }else{
            showAlert("移除关键词失败."+resp.message);
        }
    });
}

$(function () {
    // initMapData();
    initVideoText();
    initVideoKeyword();
    initVideoExtEvent();
});