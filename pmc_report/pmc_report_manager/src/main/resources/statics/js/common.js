//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false,
	complete: function(XMLHttpRequest, textStatus) {
	        if (textStatus == 'timeout') {
	        $.modal.alertWarning("服务器超时，请稍后再试！");
	        $.modal.closeLoading();
	    } else if (textStatus == "parsererror") {
	        $.modal.alertWarning("服务器错误，请联系管理员！");
	        $.modal.closeLoading();
	    }
	}
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

function isNull(val){
	if(val!=''&& val!=null){
		return val
	}
	return '';
}

//校验 true：空   false:非空 值 
function isNullOrBlank(value){
   if(value == 'undefined' 
	   || value==null 
	   || (value+'').trim()==''
	   || (value+"").trim()==""){
	   return true;
   }else{
	   return false;
   }
}

//根据Id设置属性
function getPorpById(id,porp){
	return $('#'+id).prop(porp);
}

function setPorpById(id,porp,val){
	$('#'+id).prop(porp, val);
}

function setCssById(id,attr,val){
	$('#'+id).css(attr, val);
}


/*
 * 初始化查询条件下拉框
 * 下拉框改变事件初始化下级下拉框
 */
function initShopSelected(){
	$.ajax({ 
        type: 'post', 
        data: '{}', 
        url: 'shop/findAllShops',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
            var selectOption ='<option value="" selected="selected" style="display: none">请选择车间</option>';
            var list = data.shopList;
            $.each(list, function(i,shop) { 
            	selectOption += '<option value="'+isNull(shop['shopNo'])+'">'+isNull(shop['shopNo']) + "</option>";
            });  
            $('#shop_search').html(selectOption);
            $('#shopNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
	setPorpById('area_search','disabled',true);
	setCssById('area_search','background-color','#EEEEEE');
	setPorpById('zone_search','disabled',true);
	setCssById('zone_search','background-color','#EEEEEE');
	setPorpById('station_search','disabled',true);
	setCssById('station_search','background-color','#EEEEEE');
	setPorpById('equ_search','disabled',true);
	setCssById('equ_search','background-color','#EEEEEE');
	setPorpById('jobId_search','disabled',true);
	setCssById('jobId_search','background-color','#EEEEEE');
}

//shop下拉框改变事件
function selectShop(){
	var workshopNo = $('#shop_search').val();
	if(!isNullOrBlank(workshopNo)){
		setPorpById('area_search','disabled',false);
		setCssById('area_search','background-color','');
		setPorpById('jobId_search','disabled',false);
		setCssById('jobId_search','background-color','');
		initAreaSelected(workshopNo);
		initJobIdSelected(workshopNo);
	}
}

//初始化area下拉框
function initAreaSelected(workshopNo){
	$.ajax({ 
        type: 'post', 
        data:{ 
        	shopNo : workshopNo //车间
        },
        url: 'area/findArea',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.areaList;
            $.each(list, function(i, area) { 
           	 selectOption += '<option value="'+isNull(area['lineNo'])+'">'+isNull(area['lineNo'])+"</option>";
            });  
            $('#area_search').html(selectOption);
            $('#areaNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

//area框改变事件
function selectArea(){
	var workshopNo = $('#shop_search').val();
	
	if(!isNullOrBlank(workshopNo)){
		var areaNo = $('#area_search').val();
		if(!isNullOrBlank(areaNo)){
			setPorpById('zone_search','disabled',false);
			setCssById('zone_search','background-color','');
			initZoneSelected(workshopNo,areaNo);
		}
	}
}

//初始化zone下拉框
function initZoneSelected(workshopNo,areaNo){
	$.ajax({ 
        type: 'post', 
        data:{ 
        	shopNo : workshopNo,//车间
        	lineNo : areaNo //区域
        },
        url: 'zone/findZone',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.zoneList;
            $.each(list, function(i, zone) { 
           	 selectOption += '<option value="'+isNull(zone['zoneNo'])+'">'+isNull(zone['zoneNo']) + "</option>";
            });  
            $('#zone_search').html(selectOption);
            $('#zoneNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

//zone下拉框改变事件
function selectZone(){
	var workshopNo = $('#shop_search').val();
	var areaNo = $('#area_search').val();
	var zoneNo = $('#zone_search').val();
	if(!isNullOrBlank(workshopNo)
			&&!isNullOrBlank(areaNo)
				&&!isNullOrBlank(zoneNo)){
		setPorpById('station_search','disabled',false);
		setCssById('station_search','background-color','');
		initStationSelected(workshopNo,areaNo,zoneNo);
	}
}

//初始化工位下拉框
function initStationSelected(workshopNo,areaNo,zoneNo){
	$.ajax({ 
        type: 'post', 
        data:{ 
        	shopNo : workshopNo,//车间
        	lineNo : areaNo, //区域
        	zoneNo : zoneNo //zone
        },
        url: 'zone/findStation',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.stationList;
            $.each(list, function(i, station) { 
            	if(station!=null){
            		selectOption += '<option value="'+isNull(station['stationNo'])+'">'+isNull(station['stationNo']) + "</option>";
            	}
            });  
            $('#station_search').html(selectOption);
            $('#stationNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

//工位下拉框改变事件
function selectStation(){
	var workshopNo = $('#shop_search').val();
	var areaNo = $('#area_search').val();
	var zoneNo = $('#zone_search').val();
	var stationNo = $('#station_search').val();
	if(workshopNo != null && workshopNo != ''
			&& areaNo != null && areaNo != ''
				&& zoneNo != null && zoneNo != ''
					&& stationNo != null && stationNo != ''){
		setPorpById('equ_search','disabled',false);
		setCssById('equ_search','background-color','');
		initEquipmentSelected(workshopNo,areaNo,zoneNo,stationNo);
	}
}

//初始化设备下拉框
function initEquipmentSelected(workshopNo,areaNo,zoneNo,stationNo){
	$.ajax({ 
        type: 'post', 
        data:{ 
        	shopNo : workshopNo,//车间
        	lineNo : areaNo, //区域
        	zoneNo : zoneNo, //zone
        	stationNo : stationNo //工位
        },
        url: 'zone/findEqu',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.equipmentList;
            $.each(list, function(i, equipment) { 
           	 selectOption += '<option value="'+isNull(equipment['equipmentNo'])+'">'+isNull(equipment['equipmentNo']) + "</option>";
            });  
            $('#equ_search').html(selectOption);
            $('#equipmentNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

//初始化JobID
function initJobIdSelected(shopNo){
	$.ajax({
		type:'post',
		data:{
			workshopNo: shopNo //车间
		},
		url:'model/findJobId',
		cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
        
            var selectOption ='<option value="" selected="selected" style="display: none">No JobId</option>';
            var list = data;
            $.each(list, function(i, jobId) { 
           	 selectOption += '<option value="'+isNull(jobId['modelNo'])+'">'+isNull(jobId['modelNo'])+"</option>";
            });  
            $('#jobId_search').html(selectOption);
            $('#jobIdNo').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
	})
}

//初始化frequency下拉框
function frequencySelected(){
	var param = 'frequency';
	$.ajax({ 
        type: 'get',  //请求的类型
        //data:'',//传到后台的参数
        url: 'model/getType/'+param,//请求地址
        cache: false,   //缓存
        async : false,  //同步
        dataType:'json', //返回的数据类型
        success: function (data) {//data为后台返回的参数
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.dict;//声明list接受后台返回的值
            $.each(list, function(i, frequency) { //遍历list
           	 selectOption += '<option value="'+isNull(frequency['value'])+'">'+isNull(frequency['value']) +"</option>";
            });  
            $('#frequency_search').html(selectOption);//将前台id为frequency_search的内容替换成声明selectOption(重写)
            $('#value').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

//初始化班次下拉框
function shiftSelected(){
	$.ajax({ 
        type: 'post',  //请求的类型
        data:'',//传到后台的参数
        url: 'shift/findShift',//请求地址
        cache: false,   //缓存
        async : false,  //同步
        dataType:'json', //返回的数据类型
        success: function (data) {//data为后台返回的参数
            var selectOption ='<option value="" selected="selected" style="display: none">All</option>';
            var list = data.tmBasShift;//声明list接受后台返回的值  '.'后面是返回的controller中的list名
            $.each(list, function(i, shift) { //遍历list
           	 selectOption += '<option value="'+isNull(shift['shiftNo'])+'">'+isNull(shift['shiftNo']) + "</option>";
            });  
            $('#shift_search').html(selectOption);//将前台id为frequency_search的内容替换成声明selectOption(重写)
            $('#value').html(selectOption);
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

function selectDate(){
	layui.use('laydate', function() {
		 var laydate = layui.laydate;
		 
		 var startDate = laydate.render({
			  elem: '#startTime',
			  trigger: 'click',
			  format: 'yyyy-MM-dd',
			  theme: 'molv',
			  //value: new Date(),
			  done: function(value, date, endDate){//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
				    intitFromDateArr(value);
			 }
		 });
		 
		 var startDate = laydate.render({
			  elem: '#endTime',
			  trigger: 'click',
			  format: 'yyyy-MM-dd',
			  theme: 'molv',
			  //value: new Date(),
			  done: function(value, date, endDate){//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
				    intitToDateArr(value);
			 }
		 });
	});
}

//初始化时间选择
function selectTime() {
	layui.use('laydate', function() {
	    var laydate = layui.laydate;
	    var startDate = laydate.render({
	        elem: '#startTime',
	        max: $('#endTime').val(),
	        theme: 'molv',
	        trigger: 'click',
	        //type: 'datetime',
	        type: 'date',
	        //format: 'yyyy-MM-dd HH:mm:ss',
	        format: 'yyyy-MM-dd',
	        istime: false, //是否开启时间选择
	        done: function(value, date) {
	            // 结束时间大于开始时间
	            if (value !== '') {
	                endDate.config.min.year = date.year;
	                endDate.config.min.month = date.month - 1;
	                endDate.config.min.date = date.date;
	                //endDate.config.min.time = date.time;
	            } else {
	                endDate.config.min.year = '';
	                endDate.config.min.month = '';
	                endDate.config.min.date = '';
	                //endDate.config.min.time = '';
	            }
	        }
	    });
	    var endDate = laydate.render({
	        elem: '#endTime',
	        min: $('#startTime').val(),
	        theme: 'molv',
	        trigger: 'click',
	        //type: 'datetime',
	        type: 'date',
	        //format: 'yyyy-MM-dd HH:mm:ss',
	        format: 'yyyy-MM-dd',
	        istime: false, //是否开启时间选择
	        done: function(value, date) {
	            // 开始时间小于结束时间
	            if (value !== '') {
	                startDate.config.max.year = date.year;
	                startDate.config.max.month = date.month - 1;
	                startDate.config.max.date = date.date;
	                //startDate.config.max.time = date.time;
	            } else {
	                startDate.config.max.year = '';
	                startDate.config.max.month = '';
	                startDate.config.max.date = '';
	                //startDate.config.max.time = '';
	            }
	        }
	    });
	});
}

function initDate(){
	$('#toDates').multiselect({
		includeSelectAllOption: true,
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true,
        //buttonWidth: 195,
        maxHeight: 300,
	}); 
	
	$('#fromDates').multiselect({
		includeSelectAllOption: true,
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true,
        //buttonWidth: 195,
        maxHeight: 300,
	});
	
	 $('.selectpicker').selectpicker();
}

function intitFromDateArr(param){
	if(param==null)return;
	$.ajax({ 
        type: 'post',  //请求的类型
        data:{
        	params : param
        },//传到后台的参数
        url: 'shop/findDates',//请求地址
        cache: false,   //缓存
        async : false,  //同步
        dataType:'json', //返回的数据类型
        success: function (data) {//data为后台返回的参数
            var selectOption;
            var list = data.dateList;//声明list接受后台返回的值  '.'后面是返回的controller中的list名
            for(var i = 0; i < list.length; i++){
            	selectOption += '<option value="'+isNull(list[i])+'">'+isNull(list[i]) + "</option>";
            }
            $('#fDate').html('');//将前台id为frequency_search的内容替换成声明selectOption(重写)
            $('#fDate').append(selectOption)
            $('#value').html(selectOption);
            $('#fDate').selectpicker('refresh');//动态刷新
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

function intitToDateArr(param){
		if(param==null)return;
		$.ajax({ 
	        type: 'post',  //请求的类型
	        data:{
	        	params : param
	        },//传到后台的参数
	        url: 'shop/findDates',//请求地址
	        cache: false,   //缓存
	        async : false,  //同步
	        dataType:'json', //返回的数据类型
	        success: function (data) {//data为后台返回的参数
	            var selectOption;
	            var list = data.dateList;//声明list接受后台返回的值  '.'后面是返回的controller中的list名
	            for(var i = 0; i < list.length; i++){
	            	selectOption += '<option value="'+isNull(list[i])+'">'+isNull(list[i]) + "</option>";
	            }  
	            $('#tDate').html('');//将前台id为frequency_search的内容替换成声明selectOption(重写)
	            $('#tDate').append(selectOption)
	            $('#value').html(selectOption);
	            $('#tDate').selectpicker('refresh');//动态刷新
	        },
	        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
	        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
	        }
	      });
}

//获取查询参数方法
function getParams(){
	var fromDate = $('#fDate').val();
	var toDate = $('#tDate').val();
	var f_date = fromDate==null?'':JSON.stringify(fromDate);
	var t_date = toDate==null?'':JSON.stringify(toDate);
	fDates = f_date.replace(new RegExp('"',"gm"),'');
	tDates = t_date.replace(new RegExp('"',"gm"),'');
	var params = {
		shop : $("#shop_search").val(),
		area : $("#area_search").val(),
		zone : $("#zone_search").val(),
		sTime : $("#startTime").val(),
		eTime : $("#endTime").val(),
		shift : $("#shift_search").val(),
		frequency : $("#frequency_search").val(),
		jobId : $("#jobId_search").val(),
		equipment : $('#equ_search').val(),
		station : $('#station_search').val(),
		fromDates : this.toObj(fromDate),
		fDate : fDates,
		tDate : tDates,
		toDates : this.toObj(toDate)
	}
	return params;
	
}

function toObj(arr){
	if(arr!=null&&arr.length>0){
		var result = {};
	    for(var a=0; a<arr.length; a++) {
	        result[a] = arr[a];
	    }
	    return result;
	}
	return;
}

/*
 * 查询方法初始化表格
 * tag 初始化时根据tag调用不同的js方法，
 */
function search(tag){
	var params = this.getParams();
	if(isNullOrBlank(params.shop)){
		  bootbox.alert('请输入查询关键条件！');
		  return;
	}
	queryReport(tag,params);
}

//重置查询条件，禁用下级下拉框
function resetParam(){
	$("#shop_search").val('');
	$("#area_search").val('');
	$("#zone_search").val('');
	$("#station_search").val('');
	$("#equ_search").val('');
	$("#startTime").val('');
	$("#endTime").val('');
	$("#shift_search").val('');
	$("#frequency_search").val('');
	$("#jobId_search").val('');
	resetTableTitle();
	setPorpById('area_search','disabled',true);
	setCssById('area_search','background-color','#EEEEEE');
	setPorpById('zone_search','disabled',true);
	setCssById('zone_search','background-color','#EEEEEE');
	setPorpById('station_search','disabled',true);
	setCssById('station_search','background-color','#EEEEEE');
	setPorpById('equ_search','disabled',true);
	setCssById('equ_search','background-color','#EEEEEE');
	setPorpById('jobId_search','disabled',true);
	setCssById('jobId_search','background-color','#EEEEEE');
}

//重置表格上查询条件列表
function resetTableTitle(){
	resetTableHeader("equFaultTableHeader");
	resetTableHeader("panelTableHeader");
	resetTableHeader("biw3oprTableHeader");
}

function resetTableHeader(tableid){
	if(tableid != null || tableid != ''){
		var trnums = $('#'+tableid+'>tbody').children('tr').length;
		//console.log(trnums);
		var tdnums = $('#'+tableid +' tr:eq(0)').children('td').length;
		//console.log(tdnums);
		for(var i = 0;i<trnums;i++){
			for(var j = 1 ;j<=tdnums ; j++){
				if(j%2 == 0){
					$('#'+tableid +' tr:eq('+i+') td:nth-child('+j+')').html("All");
				}
			}
		}
		
		$('#'+tableid+' tr:last-child td:last-child').html('');
		
		if(tableid == "panelTableHeader"){
			$('#'+tableid+' tr:eq(4) td:eq(3)').html('');
		}
		
		if(tableid == "biw3oprTableHeader"){
			$('#'+tableid + ' tr:eq(1) td:eq(3)').html('');
		}
	}
}
