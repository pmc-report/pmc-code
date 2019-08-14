$(function () {
	
	//初始化工厂及下级
	initShopSelected();	
	//初始化时间频率
//	frequencySelected();
	shiftSelected();
	//初始化表格上的查询条件
	inittableTitle();
	//初始化表格
	initTable();
	//initEchart();
});

function inittableTitle(params) {
	$('#cycleTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>设备</td>'
			+ '<td></td>'
			+ '<td>时间范围从</td>'
			+ '<td></td>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>工位</td>'
			+ '<td></td>'
			+ '<td>车型</td>'
			+ '<td></td>'
			+ '<td>到</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#cycleTableHeader').html(table);
//	console.log($('#cycleTableHeader tr:eq(0)').children('td').length);   获取首个tr下td 的个数
	var lengths = $('#cycleTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <= lengths;i++){
		if(i % 2 != 0){
			$('#cycleTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#cycleTableHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#cycleTableHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#cycleTableHeader td:nth-child(1)').css("border-left","1px solid #000");
	$('#cycleTableHeader tr:last-child td:last-child').html('');
	
	if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#cycleTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#cycleTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#cycleTableHeader tr:eq(0) td:eq(3)').html(params.zone);
		}else{
			$('#cycleTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.equipment != null && params.equipment.trim() != ''){
			$('#cycleTableHeader tr:eq(0) td:eq(5)').html(params.equipment);
		}else{
			$('#cycleTableHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#cycleTableHeader tr:eq(0) td:eq(7)').html(params.sTime);
		}else{
			$('#cycleTableHeader tr:eq(0) td:eq(7)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#cycleTableHeader tr:eq(0) td:eq(9)').html(params.shift);
		}else{
			$('#cycleTableHeader tr:eq(0) td:eq(9)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#cycleTableHeader tr:eq(1) td:eq(1)').html(params.area);
		}else{
			$('#cycleTableHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		if(params.station != null && params.station.trim() != ''){
			$('#cycleTableHeader tr:eq(1) td:eq(3)').html(params.station);
		}else{
			$('#cycleTableHeader tr:eq(1) td:eq(3)').html("All");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#cycleTableHeader tr:eq(1) td:eq(5)').html(params.jobId);
		}else{
			$('#cycleTableHeader tr:eq(1) td:eq(5)').html("All");
		}
		
		if(params.eTime != null && params.eTime.trim() != ''){
			$('#cycleTableHeader tr:eq(1) td:eq(7)').html(params.eTime);
		}else{
			$('#cycleTableHeader tr:eq(1) td:eq(7)').html("All");
		}
		
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#cycleTableHeader tr:eq(1) td:eq(9)').html(createTime);
	}
}

function Appendzero(obj){
	if (obj < 10) {
		return "0" + "" + obj;
	} else {
		return obj;
	}
}

function queryReport(tag,params){
	console.log(params)
	if(isNullOrBlank(params.sTime)){
		alert("请选择开始时间");
    	return ;
	}
	if(isNullOrBlank(params.eTime)){
		alert("请选择查结束时间");
    	return ;
	}
	
	var url = baseURL + 'report/cycle/list';
	if(tag == 'Biw3Cycle'){
		inittableTitle(params);
		initTable(url,params);
		initEchart(params)
	}
	clearForm("fromexport");
	setPorpById('cycleBtn','disabled',false);
	
}

function exportAll(queryParams){
	$.ajax({ 
        type: 'post', 
        data:{ 
        	area : queryParams.area,
			zone : queryParams.zone,
			eTime: queryParams.eTime,
			sTime: queryParams.sTime,
			shift: queryParams.shift,
			shop: queryParams.shop,
			jobId : queryParams.jobId,
			station : queryParams.station,
			equipment : queryParams.equipment
        },
        url: baseURL + 'report/cycle/exportAll',
        cache: false,  
        async : false,  //同步
        dataType:'json'
      });
}

/**
 * 时间秒数格式化
 * @param s 时间戳（单位：秒）
 * @returns {*} 格式化后的时分秒
 */
var sec_to_time = function(s) {
    var t;
    if(s > -1){
        var hour = Math.floor(s/3600);
        var min = Math.floor(s/60) % 60;
        var sec = s % 60;
        if(hour < 10) {
            t = '0'+ hour + ":";
        } else {
            t = hour + ":";
        }

        if(min < 10){t += "0";}
        t += min + ":";
        if(sec < 10){t += "0";}
        t += sec.toFixed(0);
    }
    return t;
}

function initTable(url,queryParams){
	var responseHandler = function (e) {
		  if (e.page.list !=null && e.page.list.length > 0) {
	          return { "rows": e.page.list, "total": e.page.totalCount };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	 var dateFormatter = function(value, row, index){
 	    return sec_to_time(value);
     } 
	 
	 var numFormatter = function(value){
		 if(value != null){
			 return value.toFixed(2);
		 }
	 }
	 var columns = [
		  {title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}},
          { field: 'station', title: '工位', align: 'center', sortable:false }, 
          { field: 'facilityId', title: '设备号', align: 'center'},
          { field: 'facilityDesc', title: '设备名称', align:'center' }, 
          { field: 'jobId', title: '车型', align: 'center'}, 
          { field: 'stdCycleTime', title: '标准节拍时间', align: 'center' },
          { field: 'designCycleTime', title: '设计节拍时间', align: 'center' },
          { field: 'minCycleTime', title: '最短节拍时间', align: 'center' },
          { field: 'maxCycleTime', title: '最长节拍时间', align: 'center' },
          { field: 'avgCycleTime', title: '平均节拍时间', align: 'center' },
          { field: 'effeciveCycleTime', title: '有效节拍时间',  align: 'center',formatter: numFormatter},
          { field: 'cycleOffset', title: '节拍时间偏移量', align: 'center',formatter: numFormatter }
      ];
     
	  $('#cycleTable').empty();
	  $('#cycleTable').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      //toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	      height: 522,						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder                                //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）          
	      pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大              //是否启用点击选中行
	      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      pageNumber: 1,                      //初始化加载第一页，默认第一页
	      pageSize: 100,                    	  //每页的记录行数（*）
	      pageList: [200, 300, 500],     	  //可供选择的每页的行数（*）  
	      smartDisplay: true,					//智能显示分页按钮
	      paginationPreText: "上一页",
	      paginationNextText: "下一页",
	      responseHandler: responseHandler,
	     
	      columns: columns,
	      queryParams : function(params) {
	    	  console.log(params);
		      return {
			    	limit : params.pageSize,
		            page : params.pageNumber,
		        
	            	area : queryParams.area,
					zone : queryParams.zone,
					eTime: queryParams.eTime,
					sTime: queryParams.sTime,
					shift: queryParams.shift,
					shop: queryParams.shop,
					jobId : queryParams.jobId,
					station : queryParams.station,
					equipment : queryParams.equipment
		      	}
		  },
	      onLoadSuccess: function (data) { 		//加载成功时执行
	    	 // exportAll(queryParams);
	    	  setPorpById('cycleBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
}

function initEchart(params){
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


	        fontSize: 10,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            }
	        }
	    }
	};
	
	$.ajax({
        type: "post",
        url: baseURL + 'report/cycle/echarts',
        data: params,
        dataType: "json",
        success: function(data){
        	var colors = ['#1E90FF','#FFA500', '#FF0000'];
        	var facilityId = [];
        	var minCycleTime = [];
      	    var maxCycleTime = [];
      	    var avgCycleTime = [];
      	    var designCycleTime = [];
  	    	for(var i=0;i<data.list.length;i++){
  	    		data.list[i].facilityId==null?0:facilityId.push(data.list[i].facilityId);
      	    	data.list[i].minCycleTime==null?0:minCycleTime.push(data.list[i].minCycleTime);
      	    	data.list[i].maxCycleTime==null?0:maxCycleTime.push(data.list[i].maxCycleTime);
      	    	data.list[i].avgCycleTime==null?0:avgCycleTime.push(data.list[i].avgCycleTime);
      	    	data.list[i].designCycleTime==null?0:designCycleTime.push(data.list[i].designCycleTime);
      	    	//designCycleTime.push(data.list[i].designCycleTime);
      	    }
      	    
  	    	var	option = {
  	    			 color : colors,
					 grid:{
						    x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                borderWidth:1,
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Cycle Time (seconds)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                        y: 'top' ,
	                        textStyle:{fontSize:13},
	                    }, 
				    //calculable: true,
				    xAxis: [{
				        	type: 'category',
				            axisTick: {show: true},
				            axisLabel :{
				                interval:0,
				                //rotate:45,
				                fontSize:10
				            },
				            data: facilityId
				        }],
				    yAxis: [{
					    	type:'value',
					    	//name:'Cycle Time(seconds)',
					    	scale:true
					    } ],
				    legend: {
				        data: ['Min Cycle Time','Max Cycle Time','Avg Cycle Time','Design Cycle Time'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    },
				    series: [
				        {
				            name: 'Min Cycle Time',
				            type: 'bar',
				            barGap: 0,
				           //label: labelOption,
				            data: minCycleTime,
				            axisLine: {
				                lineStyle: {
				                    color: colors[0]
				                }
				            }
				        },
				        {
				            name: 'Max Cycle Time',
				            type: 'bar',
				            //label: labelOption,
				            data: maxCycleTime,
				            axisLine: {
				                lineStyle: {
				                    color: colors[1]
				                }
				            }
				        },
				        {
				            name: 'Avg Cycle Time',
				            type: 'bar',
				            //label: labelOption,
				            data: avgCycleTime,
				            axisLine: {
				                lineStyle: {
				                    color: colors[2]
				                }
				            }
				        },
				        {
				            name: 'Design Cycle Time',
				            type: 'line',
				            //label: labelOption,
				            data: designCycleTime,
				            axisLine: {
				                lineStyle: {
				                    color: colors[0]
				                }
				            }
				        }
				    ]
				};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart.resize;
	        echart.setOption(option);
        }
	});
	
}

function exportcycle(param) {
	    var area = $("#area_search").val();
		var zone = $("#zone_search").val();
		var station = $("#station_search").val();
		var equipment = $("#equ_search").val();
		var jobId = $("#jobId_search").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var shift = $("#shift_search").val();
		//console.log(param);
		 
	
	var params = {
			shop : $("#shop_search").val(),
			area : isNullOrBlank(area)?'':area,
			zone : isNullOrBlank(zone)?'':zone,
			station : isNullOrBlank(station)?'':station,
			shift : isNullOrBlank(shift)?'':shift,
			equiment : isNullOrBlank(equipment)?'':equipment,
			jobId : isNullOrBlank(jobId)?'':jobId,
			sTime : startTime,
			eTime : endTime,
			exportType: param
	
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
	document.getElementById("fromexport").action = baseURL +'report/cycle/exportCycle';
	console.log(params);
	$("#fromexport").submit();

}

function createReportInput(params){
	var inputParams = '';
	var inputSearch = '';
	debugger
	for(var index in params){
		inputSearch += '<input type = "hidden" name = "'+index+'" value = "'+params[index]+'"/>'
	}
	inputParams += inputSearch;
	$("#fromexport").prepend(inputParams);
 }



