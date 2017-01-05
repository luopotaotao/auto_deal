/**
 * Created by tt on 2017/1/5.
 */
$($.lt_id_data.id_sel_times).val(times).keyup()
})
;$($.lt_id_data.id_sel_insert).unbind("click").click(function () {
    var nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
    var times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
    var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
    var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
    var mid = $.lt_method_data.methodid;
    var current_positionsel = $.lt_position_sel;
    var current_methodtitle = $.lt_method_data.title;
    var current_methodname = $.lt_method_data.name;
    if (current_positionsel.length > 0 && $.lt_rxmode == true) {
        if (current_positionsel.length < $.lt_method_data.numcount) {
            $.alert(lot_lang.am_s37.replace("%s", $.lt_method_data.numcount).replace("%m", current_methodtitle));
            return
        }
    }

    if (datas.positiondesc == "") {
        msg += "<tr><td>" + datas.methodname + "</td><td>" + datas.modename + '</td><td colspan="2">' + datas.desc + "</td></tr>"
    } else {
        msg += "<tr><td>" + datas.methodname + "</td><td>" + datas.modename + "</td><td>" + datas.desc + "</td><td>" + datas.positiondesc + "</td></tr>"
    }
});
msg += "</table></div>";
btmsg = '<div class="binfo"><span class=bbl></span><span class=bbm>' + (istrace == true ? lot_lang.dec_s16 + ": " + JsRound($.lt_trace_money, 2, true) : lot_lang.dec_s9 + ": " + $.lt_total_money) + " " + lot_lang.dec_s3 + "</span><span class=bbr></span></div>";
if ($(".layui-layer").length == 0 && $.lt_total_nums && $.lt_total_money) {
    $("#lt_total_nums").val($.lt_total_nums);
    $("#lt_total_money").val($.lt_total_money);
    $("#tk02int_con_id").html(lot_lang.am_s144.replace("[count]", $.lt_trace_issue));
    $("#tk02").show();
    $("#td02_money").html($("#lt_trace_hmoney").html());
    $("#tk02_content").html($("#lt_cf_content").html());
    $(".reveal-modal-close").on("click", function () {
        $(this).parents(".reveal-modal").parent().hide()
    });
    $("#tk02 .reveal-modal-submit").unbind("click").on("click", function () {
        $(this).parents(".reveal-modal").parent().hide();
        ajaxSubmit(me)
    })
}
})}
;$.fn.fastSubmit = function () {
    var me = this;
    $(this).click(function () {
        $.fn.fastBet()
    })
};
function checkTimeOut() {
    if ($.lt_time_leave <= 0) {
        $.confirm(lot_lang.am_s99.replace("[msg]", lot_lang.am_s15), function () {
            $.lt_reset(false);
            $.lt_ontimeout()
        }, function () {
            $.lt_reset(true);
            $.lt_ontimeout()
        }, "", 450);
        return false
    } else {
        return true
    }
}
