$(function () {
	//初始化工厂及下级
	initShopSelected();	
	//班次下拉框
	shiftSelected();
	
	//初始化查询条件展示栏
	inittadtableTitle();
	
	//初始化MS表格
	//inittadmsTable();
	//初始化Faults表格
	//inittadfaultsTable();
	selectChange();
});

function inittadtableTitle(params) {
	$('#tadTableTitleHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>时间范围从</td>'
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
			+ '<td>到</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#tadTableTitleHeader').html(table);
	$('#tadTableTitleHeader td:nth-child(1)').css("border-left","1px solid #000");
	var lengths = $('#tadTableTitleHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#tadTableTitleHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#tadTableTitleHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#tadTableTitleHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#tadTableTitleHeader tr:eq(0) td:last-child').html('');
	$('#tadTableTitleHeader tr:eq(1) td:eq(5)').html('');
	$('#tadTableTitleHeader tr:eq(1) td:last-child').html('');
		if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#tadTableTitleHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#tadTableTitleHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#tadTableTitleHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#tadTableTitleHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#tadTableTitleHeader tr:eq(0) td:eq(5)').html(params.zone);
		}else{
			$('#tadTableTitleHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#tadTableTitleHeader tr:eq(1) td:eq(1)').html(params.shift);
		}else{
			$('#tadTableTitleHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#tadTableTitleHeader tr:eq(0) td:eq(7)').html(params.sTime);
		}else{
			$('#tadTableTitleHeader tr:eq(0) td:eq(7)').html("All");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#tadTableTitleHeader tr:eq(1) td:eq(3)').html(params.jobId);
		}else{
			$('#tadTableTitleHeader tr:eq(1) td:eq(3)').html("All");
		}
		
		if(params.eTime != null && params.eTime.trim() != ''){
			$('#tadTableTitleHeader tr:eq(1) td:eq(7)').html(params.eTime);
		}else{
			$('#tadTableTitleHeader tr:eq(1) td:eq(7)').html("All");
		}
		
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#tadTableTitleHeader tr:eq(1) td:eq(9)').html(createTime);
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
	
	var sTime = params.sTime;
	var eTime = params.eTime;
	var area = params.area;
	var zone = params.zone;
	if(isNullOrBlank(area)){
		alert("请选择区域");
    	return ;
	}
	if(isNullOrBlank(zone)){
		alert("请选择Zone");
    	return ;
	}
	if(isNullOrBlank(sTime)){
		alert("请选择开始时间");
    	return ;
	}
	if(isNullOrBlank(eTime)){
		alert("请选择结束时间");
    	return ;
	}
	
	var url = baseURL + 'report/detail/list';
	
	$("#tadTableStyle").html('');
	
	if(tag=='TADETAIL'){
		
		inittadtableTitle(params);
		//(params);
		//inittadfaultsTable(params);
		queryMSInfo(params);
	}
	clearForm("fromexport");
	setPorpById('detailBtn','disabled',false);
}

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

/*function inittadmsTable(queryParams){
	
	var responseHandler = function (e) {
	      //console.log(e);
		  if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.totalCount };
	      } else {
	          return { "rows": [], "total": 0};
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
	   	{ field: 'equipment', title: 'MS', align: 'center'},
        { field: 'workDay', title: 'Date', align: 'center'},
        { field: 'tarTechAvail', title: 'Target TA', align: 'center',formatter: numFormatter}, 
        { field: 'techAvail', title: 'TA', align: 'center',formatter: numFormatter},
        { field: 'goodPartCount', title: 'Good Part Count', align:'center'}, 
        { field: 'downTime', title: 'Downtime', align: 'center',formatter: numFormatter}, 
        { field: 'faultOcc', title: 'Occ', align: 'center'},
        { field: 'buildTime', title: 'Build Time', align: 'center',formatter: numFormatter}
    ];
   
	  $('#tad_MS').empty();
	  $('#tad_MS').bootstrapTable('destroy').bootstrapTable({
	      url: 'detail/listMS',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	    //sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	     // pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      //showColumns: true,                  //是否显示所有的列
	      //showRefresh: true,                  //是否显示刷新按钮
	      //showToggle:true,                    //是否显示详细视图和列表视图
	      clickToSelect: true,                //是否启用点击选中行
	      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      pageNumber: 1,                      //初始化加载第一页，默认第一页
	      pageSize: 100,                    	  //每页的记录行数（*）
	      pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
	      //showExport: true,  				  //是否显示导出按钮  
		  exportDataType:'all', 			  //导出所有数据
	      buttonsAlign:"right",  			  //按钮位置  
	      exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
	      Icons:'glyphicon-export',  
	     // smartDisplay: true,					//智能显示分页按钮
	     // paginationPreText: "上一页",
	     // paginationNextText: "下一页",
	      responseHandler: responseHandler,
	      hasPreviousPage: true,
	     // hasNextPage: true,
	    //  lastPage: true,
	    //  firstPage: true,
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
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
}*/

/*function inittadfaultsTable(queryParams){
	
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
		 
		 var dateFormatter = function(value, row, index){
		    return sec_to_time(value);
	   } 
	   var columns = [
		   	{ field: 'station', title: 'MS', align: 'center'},
	        { field: 'occurence', title: 'Occ', align: 'center'},
	        { field: 'minutes', title: 'Minutes', align: 'center',}, 
	        { field: 'facilityId', title: 'Station', align: 'center',},
	        { field: 'faultDescription', title: 'Description', align:'center',}, 
	    ];
	   
		  $('#tad_Faults').empty();
		  $('#tad_Faults').bootstrapTable('destroy').bootstrapTable({
		      url: 'detail/listFaults',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
		      method: "post",                     //使用get请求到服务器获取数据
		      dataType: "json",
		      contentType: "application/x-www-form-urlencoded",
		      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
		    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
		      cache: false,                       // 不缓存
		      striped: true,                      // 隔行加亮
		      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
		                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
		      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		      sortable: true,                     //是否启用排序;意味着整个表格都会排序
		    //sortName: 'taEquFaultId',           // 设置默认排序为 name
		      sortOrder: "asc",                   //排序方式
		     // pagination: true,                   //是否显示分页（*）
		      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		      strictSearch: true,
		      //showColumns: true,                  //是否显示所有的列
		      //showRefresh: true,                  //是否显示刷新按钮
		      //showToggle:true,                    //是否显示详细视图和列表视图
		      clickToSelect: true,                //是否启用点击选中行
		      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
		      pageNumber: 1,                      //初始化加载第一页，默认第一页
		      pageSize: 100,                    	  //每页的记录行数（*）
		      pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
		      //showExport: true,  				  //是否显示导出按钮  
			  exportDataType:'all', 			  //导出所有数据
		      buttonsAlign:"right",  			  //按钮位置  
		      exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
		      Icons:'glyphicon-export',  
		     // smartDisplay: true,					//智能显示分页按钮
		     // paginationPreText: "上一页",
		     // paginationNextText: "下一页",
		      responseHandler: responseHandler,
		      hasPreviousPage: true,
		     // hasNextPage: true,
		    //  lastPage: true,
		    //  firstPage: true,
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
		      exportOptions : {  
		          ignoreColumn: [0],  				//忽略某一列的索引  
		          fileName: 'TA DETAIL报表',  			//文件名称设置  
		          worksheetName: 'TA DETAIL',  			//表格工作区名称  
		          tableName: 'TA DETAIL报表',  
		          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
		          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
					               var result = "";  
						               if (row > 0 && col == 0)  
					                   result = "\\@";  
					               return result;  
	           		}    
		      }, 
		      onLoadSuccess: function (data) { 		//加载成功时执行
		          //console.log(data);
		    	  setPorpById('detailBtn','disabled',false);
		      },
		      onLoadError: function (res) { 		//加载失败时执行
		          //console.log(res);
		      }
		  });
	}*/

function queryMSInfo(queryParams){
	
	$.ajax({ 
        type: 'post', 
        data:{ 
        	area : queryParams.area,
			zone : queryParams.zone,
			eTime: queryParams.eTime,
			sTime: queryParams.sTime,
			shift: queryParams.shift,
			shop: queryParams.shop,
			jobId : queryParams.jobId
        },
        url: 'detail/list',
        cache: false,  
        async : false,  //同步
        dataType:'json', 
        success: function (data) {
        	debugger
	    	var map = data.map;
	    	$.each(map,function(key,value){
	            console.info("key: " + key + ", Value: " + value );
	            var params = value;
	    		createMsTable(key,params);
	    	})
           
        },
        error: function (data, XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert('data:'+typeof(data) +",XMLHttpRequest:"+XMLHttpRequest+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
        }
      });
}

function createMsTable(key,params){
	
	var str = JSON.stringify(params[0].msList);
	var msData = JSON.parse(str);
	
	var msTab =  $('<table id ="msTab" class="table table-striped table-bordered"></table>');
	
	var columns = [
		 	[{ title: key, halign : "center",align: 'center',colspan : 7}],
	   	[{ field: 'workDay', title: '日期', align: 'center'},
	    { field: 'tarTechAvail', title: '目标TA', align: 'center'}, 
	    { field: 'techAvail', title: 'TA', align: 'center'},
	    { field: 'goodPartCount', title: '合格件', align:'center'}, 
	    { field: 'downTime', title: '停机时间', align: 'center'}, 
	    { field: 'faultOcc', title: '次数', align: 'center'},
	    { field: 'buildTime', title: '生产时间', align: 'center'}]
	];
	
	msTab.bootstrapTable('destroy').bootstrapTable({
	      striped: true, 
	      columns: columns,
	      data:msData
	});
	 
	 $("#tadTableStyle").append(msTab);
	 
	 var faultTab =  $('<table id ="faultTab" class="table table-striped table-bordered"></table>');
	 
	 var str = JSON.stringify(params[0].faultList);
	 var faultData = JSON.parse(str);
	
	 var columns = [
		 	[{ title: key +' Fault', halign : "center",align: 'center',colspan : 7}],
		 	[{ field: 'occurence', title: '次数', align: 'center'},
		     { field: 'minutes', title: '分钟', align: 'center',}, 
		     { field: 'facilityId', title: '工位', align: 'center',},
		     { field: 'faultDescription', title: '故障描述', align:'center',}]
		 ];
		
	 faultTab.bootstrapTable('destroy').bootstrapTable({
	      striped: true, 
	      columns: columns,
	      data:faultData
	  });
	 
	 $("#tadTableStyle").append(faultTab);
}



function exportdetail(param) {
	
	var area = $("#area_search").val();
	var zone = $("#zone_search").val();
	var jobId = $("#jobId_search").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var shift = $("#shift_search").val();
	 

	var params = {
		shop : $("#shop_search").val(),
		area : isNullOrBlank(area)?'':area,
		zone : isNullOrBlank(zone)?'':zone,
		shift : isNullOrBlank(shift)?'':shift,
		jobId : isNullOrBlank(jobId)?'':jobId,
		sTime : startTime,
		eTime : endTime,
		exportType: param
		} ;

		createReportInput(params);
		document.getElementById("fromexport").action = baseURL +'modules/report/detail/exportDetail';
		$("#fromexport").submit();
}

function createReportInput(params){
	var inputParams = '';
	var inputSearch = '';
	
	for(var index in params){
		inputSearch += '<input type = "hidden" name = "'+index+'" value = "'+params[index]+'"/>'
	}
	inputParams += inputSearch;
	$("#fromexport").prepend(inputParams);
 }
