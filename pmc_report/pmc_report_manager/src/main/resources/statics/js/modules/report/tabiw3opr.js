$(function () {
	//初始化工厂及下级
	initShopSelected();	
	
	shiftSelected();
	
	//初始化查询条件展示栏
	initbiw3oprTableTitle();
	
	//初始化AreaOpr表格
	initAreaTable();
	//初始化ZoneOpr表格
	initZoneTable();
});


function initbiw3oprTableTitle(params){

	$('#biw3oprTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>日期</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#biw3oprTableHeader').html(table);
//	console.log($('#equFaultTableHeader tr:eq(0)').children('td').length);   获取首个tr下td 的个数
	var lengths = $('#biw3oprTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#biw3oprTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"10%","text-align":"center"});
		}else{
			$('#biw3oprTableHeader td:nth-child('+i+')').html("All");
			$('#biw3oprTableHeader td:nth-child('+i+')').css("width","23%");
		}
	}
	$('#biw3oprTableHeader td:nth-child(1)').css("border-left","1px solid #000");
	$('#biw3oprTableHeader tr:eq(1) td:eq(3)').html('');
	$('#biw3oprTableHeader tr:last-child td:last-child').html('');
	
	if(params != null && params != ''){
		console.log(params)
		if(params.shop != null && params.shop.trim() != ''){
			$('#biw3oprTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#biw3oprTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#biw3oprTableHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#biw3oprTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#biw3oprTableHeader tr:eq(0) td:eq(5)').html(params.shift);
		}else{
			$('#biw3oprTableHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#biw3oprTableHeader tr:eq(1) td:eq(1)').html(params.sTime);
		}else{
			$('#biw3oprTableHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		$('#biw3oprTableHeader tr:eq(1) td:eq(3)').html("");
		
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#biw3oprTableHeader tr:last-child td:last-child').html(createTime);
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
	
	var url = baseURL + 'report/opr/list';
	if(tag=='Biw3OPR'){
		
		initbiw3oprTableTitle(params);
		initAreaTable(url,params);
		initZoneTable(url,params);
	}
}

function initAreaTable(url,queryParams){
	
	var responseHandler = function (e) {
	      if (e.area && e.area.length>0) {
	          return { "rows": e.area, "total": e.area.length };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
   var columns = [
        { field: 'area', title: '区域', align: 'center'},
        { field: 'actual', title: '实际产量', align: 'center'}, 
        { field: 'shiftPlan', title: '班次计划产量', align: 'center' },
        { field: 'variation', title: '偏差', align:'center' }, 
        { field: 'productionOpr', title: '生产OPR', align: 'center'}, 
        { field: 'equipmentOpr', title: '设备OPR', align: 'center' }
    ];
   
	  $('#areaTab').empty();
	  $('#areaTab').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
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
	      pageSize: 10,                    	  //每页的记录行数（*）
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
			    	//limit : params.pageSize,
		           // page : params.pageNumber,
		            //keyword: params.search,//搜索
					//sortOrder: params.order,//排序
					//sortName: params.sort,//排序字段
	            	area : queryParams.area,
					shift: queryParams.shift,
					shop: queryParams.shop,
					sTime: queryParams.sTime,
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: '区域OPR报表',  			//文件名称设置  
	          worksheetName: '区域 OPR',  			//表格工作区名称  
	          tableName: '区域OPR报表',  
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
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
}

function initZoneTable(url,queryParams){
	
	var responseHandler = function (e) {
	     // console.log(e);
	      if (e.zoneList && e.zoneList.length > 0) {
	          return { "rows": e.zoneList, "total": e.zoneList.lengtht };
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
        
  	  	{title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1}},
  	  	{ field: 'zone', title: 'Zone', align: 'center', sortable:false }, 
        { field: 'goodPartCount', title: '合格件产量计数', align: 'center' },
        { field: 'downTime', title: '停机时间', halign:'center',formatter: dateFormatter }, 
        { field: 'productionOpr', title: '生产OPR', align: 'center'}, 
        { field: 'equipmentOpr', title: '设备OPR', align: 'center' },
        { field: 'equipAvail', title: '设备可用率', halign:'center' }, 
        { field: 'cycleTime', title: '节拍时间', halign:'center' },
    	{ field: 'starved', title: '堵料时间', align: 'center', formatter: dateFormatter },
        { field: 'blocked', title: '缺料时间', halign:'center',formatter: dateFormatter }
    ];
   
	  $('#zoneTab').empty();
	  $('#zoneTab').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列
						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
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
	      pageSize: 10,                    	  //每页的记录行数（*）
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
			    	//limit : params.pageSize,
		           // page : params.pageNumber,
		            //keyword: params.search,//搜索
					//sortOrder: params.order,//排序
					//sortName: params.sort,//排序字段
	            	area : queryParams.area,
					shift: queryParams.shift,
					shop: queryParams.shop,
					sTime: queryParams.sTime
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: 'ZoneOPR报表',  			//文件名称设置  
	          worksheetName: 'ZoneOPR',  			//表格工作区名称  
	          tableName: 'ZoneOPR报表',  
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
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
	  
}

function oprsubmit(){
	  $("#oprshop").val($("#shop_search").val());
	  $("#oprarea").val($("#area_search").val());
	  $("#oprshift").val($("#shift_search").val());
	  $("#oprstime").val($("#startTime").val());
	  if($("#oprshop").val() == null ||$.trim($("#oprshop").val()) == '' ){
		  alert("车间不可为空！");
	  }else if($("#oprarea").val() == null || $.trim($("#oprarea").val()) == ''){
		  alert("区域不可为空！");
	  }else if($("#oprshift").val() == null || $.trim($("#oprshift").val()) == ''){
		  alert("班次不可为空！");
	  }else if($("#oprstime").val()  == null || $.trim($("#oprstime").val()) == ''){
		  console.log($("#oprstime").val());
		  alert("日期不可为空！");
	  }else{
		  $("#oprform").attr("action","/pmc/report/opr/exportOpr");
		  $("#oprform").submit();
	  }
	
}