$(function () {
	//初始化工厂及下级
	initShopSelected();	
	//班次下拉框
	shiftSelected();
	
	//初始化查询条件展示栏
	initTableTitle();
	
	//初始化表格
	initTASummaryTable();
	
	selectChange();
});

function initTableTitle(params) {
	$('#taSTableTitleHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>对比周</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '<td>车型</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td>当前周</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#taSTableTitleHeader').html(table);
	$('#taSTableTitleHeader td:nth-child(1)').css("border-left","1px solid #000");
	var lengths = $('#taSTableTitleHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#taSTableTitleHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#taSTableTitleHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#taSTableTitleHeader td:nth-child('+i+')').html("");
		}
	}
	
	$('#taSTableTitleHeader tr:eq(1) td:eq(4)').html("");
	$('#taSTableTitleHeader tr:eq(1) td:eq(5)').html("");
	
	if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#taSTableTitleHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#taSTableTitleHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#taSTableTitleHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#taSTableTitleHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#taSTableTitleHeader tr:eq(0) td:eq(5)').html(params.zone);
		}else{
			$('#taSTableTitleHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#taSTableTitleHeader tr:eq(0) td:eq(7)').html(params.sTime);
		}else{
			$('#taSTableTitleHeader tr:eq(0) td:eq(7)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#taSTableTitleHeader tr:eq(1) td:eq(1)').html(params.shift);
		}else{
			$('#taSTableTitleHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#taSTableTitleHeader tr:eq(1) td:eq(3)').html(params.jobId);
		}else{
			$('#taSTableTitleHeader tr:eq(1) td:eq(3)').html("All");
		}
		
		if(params.eTime != null && params.eTime.trim() != ''){
			$('#taSTableTitleHeader tr:eq(1) td:eq(7)').html(params.eTime);
		}else{
			$('#taSTableTitleHeader tr:eq(1) td:eq(7)').html("All");
		}
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#taSTableTitleHeader tr:eq(1) td:eq(9)').html(createTime);
	}
}
//周数
var preWeekNo = 0;
var curWeekNo = 0;

function queryReport(tag,params){
	
	var sTime = params.sTime;
	var eTime = params.eTime;
	var area = params.area;
	var zone = params.zone;
	var jobId = params.jobId
	if(isNullOrBlank(sTime)){
		alert("请选择对比开始周");
    	return ;
	}
	if(isNullOrBlank(eTime)){
		alert("请选择当前周");
    	return ;
	}
	if(isNullOrBlank(area)){
		alert("请选择区域");
    	return ;
	}
	if(isNullOrBlank(zone)){
		alert("请选择Zone");
    	return ;
	}
	if(isNullOrBlank(jobId)){
		alert("请指定车型");
    	return ;
	}
	
	var url = baseURL + 'report/summary/info';
	if(tag=='TAS'){
		
		initTableTitle(params);
		initTASummaryTable(url,params);
		echars(params);
	}
	clearForm("fromexport");
	setPorpById('exportBtn','disabled',true);
}



function initTASummaryTable(url,queryParams){
	
	if(queryParams!=null){
		preWeekNo = getWeek(queryParams.sTime);
		curWeekNo = getWeek(queryParams.eTime)
	}
	//console.log(preWeekNo)
	var responseHandler = function (e) {
	      //console.log(e);
	      if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.totalCount };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	 var cellFormatter = function(value,row,index){
     	return {css:{'padding':'0','margin':'0'}}
 	 }
	 
	 var columns = [
	   [{
		   title : "BODYSHOP3 % TA SUMMARY",
           halign : "center",
           align : "center",
           colspan : 12
	   	
	   }],
	   [
		   {title : '&nbsp', halign : "center",align : "center",colspan : 4},
		   {title : 'Comparison Week:对比周'+preWeekNo,halign : "center",align : "center",colspan : 3},
		   {title : 'Current Week:当前周'+curWeekNo,halign : "center",align : "center",colspan : 3},
		   {title : 'Improvement Week On Week-每周改善',halign : "center",align : "center",colspan : 2},
	   ],
	   [{ field: 'area', title: 'Area', align: 'center'},
        { field: 'zone', title: 'Zone', align: 'center'}, 
        { field: 'jobId', title: 'Job Id', align: 'center',},
        { field: 'targetTa', title: 'Target TA%', align:'center'}, 
        { field: 'preActualTa', title: 'Actual TA %', align: 'center'}, 
        { field: 'preGap', title: 'Gap to Taget', align: 'center'},
        { field: 'preVol', title: 'Prod Vol', align: 'center'},
        { field: 'curActualTa', title: 'Actual TA %', align: 'center'}, 
        { field: 'curGap', title: 'Gap to Taget', align: 'center'},
        { field: 'curVol', title: 'Prod Vol', align: 'center'},
        { field: 'improve', title: '%', align: 'center'},
        { field: 'trend', title: 'TREND', align: 'center',cellStyle:cellFormatter,formatter:function (value,row,index){
         	var src = '';
        	if("1"==value){
        		src = baseURL+'/statics/image/up.png';
        	}else if("3"==value){
        		src = baseURL+'/statics/image/fair.png';
        	}else{
        		src = baseURL+'/statics/image/down.png';
        	}
            return '<img  src='+src+' style="width:100%;height:100%;">';
         }}
       ]
    ];
   
	  $('#TASummaryTable').empty();
	  $('#TASummaryTable').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortOrder: "asc",                   //排序方式
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,                 //是否显示详细视图和列表视图
	      clickToSelect: true,                //是否启用点击选中行
	      buttonsAlign:"right",  			  //按钮位置  
	      hasPreviousPage: true,
	      responseHandler: responseHandler,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					eTime: queryParams.eTime,
					sTime: queryParams.sTime,
					shift: queryParams.shift,
					shop: queryParams.shop,
					jobId : queryParams.jobId,
		      	}
		  },
	      onLoadSuccess: function (data) { 		//加载成功时执行
	          //console.log(data);
	    	  setPorpById('exportBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
}

function echars(params){
	var posList = [
	    'left', 'right', 'top', 'bottom',
	    'inside',
	    'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
	    'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
	];

	echart.config = {
	    rotate: 0,
	    align: 'middle',
	    verticalAlign: 'middle',
	    position: 'top',
	    distance: 15,
	};
	var labelOption = {
	    normal: {
	        show: true,
	        position: echart.config.position,
	        distance: echart.config.distance,
	        fontSize: 15,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            }
	        }
	    }
	};
	$.ajax({
        type: "post",
        url: baseURL + 'report/summary/chart',
        data: params,
        dataType: "json",
        success: function(data){
        	var date = [];
      	    var targetArr = [];
      	    var taArr = [];
      	    if(data.list!=null&&data.list.length>0){
      	    	 for(var i=0;i<data.list.length;i++){
           	    	date.push(data.list[i].week);
           	    	data.list[i].targetTa==null?targetArr.push(0):targetArr.push(data.list[i].targetTa);
           	    	data.list[i].curActualTa==null?taArr.push(0):taArr.push(data.list[i].curActualTa);
           	    }
      	    }

        	var	option = {
					 grid:{
						 	x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                borderWidth:1,
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Zone Technical Availability Trend Chart',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:12},
	                    }, 
	                    
	                color: ['#FFA500', '#6fa8dc'],
				    xAxis: [{
				            type: 'category',
				            //boundaryGap: false,
				            //axisLine:{onZero:false},
				            axisTick: {show: true},
				            axisLabel :{
				                interval:0,
				                fontSize:15
				            },
				            data: date
				        }],
				    yAxis: [{
					    	type:'value',
					    	max:100,
					    	min:-100,
					    	splitNumber:20
				    	}],
				    legend: {
				        data: ['TA','Target TA'],
				    	x:'right',
				    	y:'top'
				    
				    },
				    series: [{
				            name: 'Target TA',
				            type: 'line',
				            barGap: 0,
				            data: targetArr,
				        },
				        {
				            name: 'TA',
				            type: 'bar',
				            label: labelOption,
				            data: taArr,
				            hoverAnimation:false,
				        },
				    ]
        	};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart.resize;
	        echart.setOption(option);
        }
	});
}

function exportSummary(param) {
	var shop = $("#shop_search").val();
	var area = $("#area_search").val();
	var zone = $("#zone_search").val();
	var shift = $("#shift_search").val();
	var jobId = $("#jobId_search").val();
	var sTime = $("#startTime").val();
	var eTime = $("#endTime").val();
	
	var params = {
			shop : shop,
			area : isNullOrBlank(area)?'ALL':area,
			zone : isNullOrBlank(zone)?'ALL':zone,
			shift : isNullOrBlank(shift)?'ALL':shift,
			jobId :jobId,
			preWeekNo:preWeekNo,
			curWeekNo:curWeekNo,
			sTime: sTime,
			eTime: eTime,
			type : param
	} ;
	//图片
	var image = new Image();
	
	image.src = echart.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff',
        width:'890px'
	});
	
	var echarepxport = image.src.replace("data:image/png;base64,", "");
	params.echarepxport = echarepxport;

	createReportInput(params);
	document.getElementById("fromexport").action = baseURL +'/report/summary/export';
	$("#fromexport").submit();
}

function createReportInput(params,tableDownTimeArray,tableOccparamsArray){
	var inputParams = '';
	var inputSearch = '';
	for(var index in params){
		inputSearch += '<input type = "hidden" name = "'+index+'" value = "'+params[index]+'"/>'
	}
	inputParams += inputSearch;
	//console.log("输入参数："+inputParams)
	$("#fromexport").prepend(inputParams);
}